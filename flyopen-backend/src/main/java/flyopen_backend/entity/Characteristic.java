package flyopen_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "characteristics", schema = "public",
        uniqueConstraints = @UniqueConstraint(columnNames = {"component_id", "code"}))
@Getter
@Setter
public class Characteristic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "component_id")
    private Component component;

    @Column(nullable = false, length = 50)
    private String code;

    @Column(nullable = false, precision = 14, scale = 4)
    private BigDecimal value;

    @Column(length = 20)
    private String unit;
}