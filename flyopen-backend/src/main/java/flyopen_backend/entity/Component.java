package flyopen_backend.entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "components", schema = "public")
@Getter
@Setter


public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(precision = 12, scale = 3)
    private BigDecimal value;

    @Column(precision = 12, scale = 3)
    private BigDecimal size;

    @Column(length = 50, unique = true)
    private String reference;

    @Column(length = 100)
    private String material;

    @Column(name = "length_mm", precision = 12, scale = 3)
    private BigDecimal lengthMm;

    @Column(name = "width_mm", precision = 12, scale = 3)
    private BigDecimal widthMm;

    @Column(name = "height_mm", precision = 12, scale = 3)
    private BigDecimal heightMm;

    @Column(name = "diameter_mm", precision = 12, scale = 3)
    private BigDecimal diameterMm;

    @Column(name = "weight_kg", precision = 12, scale = 3)
    private BigDecimal weightKg;

    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;

    @Column
    private Integer stock = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(length = 50)
    private String type;

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Characteristic> characteristics = new ArrayList<>();

    public Component() {
    }


}
