package flyopen_backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "drones_nodes", schema = "public")
@Getter
@Setter
public class DroneNode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drone_id")
    private Drone drone;

    // null = raíz del árbol
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private DroneNode parent;

    // Tipo de pieza que admite este hueco (Motor, Hélice...)
    @Column(name = "slot_type", nullable = false, length = 50)
    private String slotType;

    // null = hueco vacío, pendiente de rellenar
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(nullable = false)
    private int quantity = 1;
}
