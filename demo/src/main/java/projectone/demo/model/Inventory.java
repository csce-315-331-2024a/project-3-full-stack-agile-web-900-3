package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity(name="Inventory")
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ensuring that the ID is auto-generated
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "low_threshold", nullable = false)
    private Integer lowThreshold;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;
}