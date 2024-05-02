package projectone.demo.model;
import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;
/**
 * Represents an overstock record in inventory.
 */
@Entity(name="Overstock")
@Table(name="overstock_view") // This could be a table or a database view
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Overstock implements Serializable {
     /**
     * The unique identifier for the inventory item.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;
    /**
     * The name of the inventory item.
     */
    @Column(name = "inventory_name", nullable = false)
    private String inventoryName;
    
    /**
     * The type category of the inventory item.
     */
    @Column(name = "inventory_type", nullable = false)
    private String inventoryType;
     /**
     * The current quantity of the inventory item in stock.
     */
    @Column(name = "current_quantity", nullable = false)
    private Integer currentQuantity;
     /**
     * The unit of measure for the inventory item.
     */
    @Column(name = "unit", nullable = false)
    private String unit;
      /**
     * The low threshold quantity that triggers a reorder.
     */
    @Column(name = "low_threshold", nullable = false)
    private Integer threshold;
     /**
     * The count of how often the inventory item is used.
     */
    @Column(name = "usage_count", nullable = false)
    private Long usageCount;
}