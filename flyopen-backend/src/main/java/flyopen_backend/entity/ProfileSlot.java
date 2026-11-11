package flyopen_backend.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile_slots", schema = "public")
@Getter
@Setter

public class ProfileSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id")
    private MissionProfile profile;

    @Column(name = "component_type", nullable = false, length = 50)
    private String componentType;

    // Tipo del que cuelga este hueco; null = raíz del árbol
    @Column(name = "parent_type", length = 50)
    private String parentType;

    @Column(nullable = false)
    private int quantity = 1;
}
