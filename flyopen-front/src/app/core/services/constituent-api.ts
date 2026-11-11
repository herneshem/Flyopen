import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

export interface Characteristic {
  code: string;
  value: number;
  unit: string | null;
}

export interface ComponentPart {
  id: number;
  name: string;
  reference: string;
  type: string | null;
  material: string | null;
  weightKg: number | null;
  stock: number | null;
  description: string | null;
  characteristics: Characteristic[];
}

interface ImportApiResponse {
  mensaje: string;
  filasProcesadas: number;
}

export interface ImportResult {
  message: string;
  processedRows: number;
}

export interface ProfileSlot {
  componentType: string;
  parentType: string | null;
  quantity: number;
}

export interface MissionProfile {
  id: number;
  code: string;
  name: string;
  slots: ProfileSlot[];
}

export interface DroneNode {
  id: number;
  parentId: number | null;
  slotType: string;
  quantity: number;
  componentId: number | null;
  componentName: string | null;
}

export interface Drone {
  id: number;
  name: string;
  profileCode: string;
  nodes: DroneNode[];
}

@Injectable({
  providedIn: 'root',
})
export class ConstituentApi {
  private http = inject(HttpClient);
  private url = 'http://localhost:8080/api/components';
  private apiUrl = 'http://localhost:8080/api';

  listParts(): Observable<ComponentPart[]> {
    return this.http.get<ComponentPart[]>(this.url);
  }

  listProfiles(): Observable<MissionProfile[]> {
  return this.http.get<MissionProfile[]>(`${this.apiUrl}/profiles`);
}

createDrone(name: string, profileId: number): Observable<Drone> {
  return this.http.post<Drone>(`${this.apiUrl}/drones`, { name, profileId });
}

assignPart(droneId: number, nodeId: number, componentId: number | null): Observable<Drone> {
  return this.http.put<Drone>(`${this.apiUrl}/drones/${droneId}/nodes/${nodeId}`, { componentId });
}

  importFile(file: File): Observable<ImportResult> {
    const form = new FormData();
    form.append('file', file);
    return this.http.post<ImportApiResponse>(`${this.url}/import`, form).pipe(
      map((response) => ({
        message: response.mensaje,
        processedRows: response.filasProcesadas,
      })),
    ); 
  }

  exportFile(): Observable<Blob> {
    return this.http.get(`${this.url}/export`, { responseType: 'blob' });
  }
}