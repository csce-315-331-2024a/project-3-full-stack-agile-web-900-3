package projectone.demo.model;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;

@Entity(name="Overstock")
@Table(name="overstock_view") // This could be a table or a database view
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Overstock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(name = "inventory_name", nullable = false)
    private String inventoryName;

    @Column(name = "inventory_type", nullable = false)
    private String inventoryType;

    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "low_threshold", nullable = false)
    private Integer threshold;

    @Column(name = "usage_count", nullable = false)
    private Long usageCount;
}