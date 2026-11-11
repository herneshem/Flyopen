import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import {
  ComponentPart, ConstituentApi, Drone, DroneNode, MissionProfile,
} from '../../core/services/constituent-api';

interface TreeRow {
  node: DroneNode;
  depth: number;
  hasChildren: boolean;
}

@Component({
  selector: 'app-constituent',
  imports: [DecimalPipe],
  templateUrl: './constituent.html',
  styleUrl: './constituent.scss',
})
export class Constituent implements OnInit {
  private api = inject(ConstituentApi);

  catalog = signal<ComponentPart[]>([]);
  profiles = signal<MissionProfile[]>([]);
  drone = signal<Drone | null>(null);
  selectedNodeId = signal<number | null>(null);
  expandedNodeIds = signal<Set<number>>(new Set());
  selected = signal<ComponentPart | null>(null);
  error = signal<string | null>(null);

  rows = computed(() => {
    const drone = this.drone();
    return drone ? this.flatten(drone.nodes, this.expandedNodeIds()) : [];
  });

  selectedNode = computed(
    () => this.drone()?.nodes.find((n) => n.id === this.selectedNodeId()) ?? null,
  );

  compatibleParts = computed(() => {
    const node = this.selectedNode();
    return node ? this.catalog().filter((part) => part.type === node.slotType) : [];
  });

  filledCount = computed(
    () => this.drone()?.nodes.filter((n) => n.componentId !== null).length ?? 0,
  );

  totalWeightKg = computed(() => {
    const drone = this.drone();
    if (!drone) return 0;
    return drone.nodes.reduce((sum, node) => {
      const part = this.catalog().find((p) => p.id === node.componentId);
      return sum + (part?.weightKg ?? 0) * node.quantity;
    }, 0);
  });

  ngOnInit(): void {
    this.api.listParts().subscribe((parts) => this.catalog.set(parts));
    this.api.listProfiles().subscribe((profiles) => this.profiles.set(profiles));
  }

  createDrone(profile: MissionProfile): void {
    this.api.createDrone(`Dron ${profile.name}`, profile.id).subscribe({
      next: (drone) => {
        this.drone.set(drone);
        const parentIds = new Set(drone.nodes.map((node) => node.parentId));
        this.expandedNodeIds.set(
          new Set(drone.nodes.filter((node) => parentIds.has(node.id)).map((node) => node.id)),
        );
        this.selectedNodeId.set(null);
        this.selected.set(null);
        this.error.set(null);
      },
      error: () => this.error.set('No se pudo crear el dron.'),
    });
  }

  selectNode(node: DroneNode): void {
    this.selectedNodeId.set(node.id);
    this.selected.set(this.catalog().find((p) => p.id === node.componentId) ?? null);
  }

  toggleNode(nodeId: number): void {
    this.expandedNodeIds.update((expanded) => {
      const next = new Set(expanded);
      if (next.has(nodeId)) {
        next.delete(nodeId);
      } else {
        next.add(nodeId);
      }
      return next;
    });
  }

  assign(part: ComponentPart): void {
    const drone = this.drone();
    const node = this.selectedNode();
    if (!drone || !node) return;

    this.api.assignPart(drone.id, node.id, part.id).subscribe({
      next: (updated) => {
        this.drone.set(updated);
        this.selected.set(part);
        this.error.set(null);
      },
      error: (e: HttpErrorResponse) =>
        this.error.set(e.error?.error ?? 'No se pudo asignar la pieza.'),
    });
  }

  clear(node: DroneNode): void {
    const drone = this.drone();
    if (!drone) return;

    this.api.assignPart(drone.id, node.id, null).subscribe({
      next: (updated) => {
        this.drone.set(updated);
        this.selected.set(null);
      },
      error: () => this.error.set('No se pudo vaciar el hueco.'),
    });
  }

  private flatten(nodes: DroneNode[], expandedNodeIds: Set<number>): TreeRow[] {
    const children = new Map<number | null, DroneNode[]>();
    for (const node of nodes) {
      const list = children.get(node.parentId) ?? [];
      list.push(node);
      children.set(node.parentId, list);
    }

    const result: TreeRow[] = [];
    const walk = (parentId: number | null, depth: number): void => {
      for (const node of children.get(parentId) ?? []) {
        const hasChildren = children.has(node.id);
        result.push({ node, depth, hasChildren });
        if (hasChildren && expandedNodeIds.has(node.id)) {
          walk(node.id, depth + 1);
        }
      }
    };
    walk(null, 0);
    return result;
  }
}