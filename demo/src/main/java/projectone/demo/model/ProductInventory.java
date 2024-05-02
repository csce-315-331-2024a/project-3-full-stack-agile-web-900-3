package projectone.demo.model;
import lombok.*;

/**
 * Represents a product inventory junction table in the system.
 */

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
/**
 * Entity class representing product inventory table.
 */
@Entity
@Table(name = "product_inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInventory implements Serializable {

    /** The unique identifier for the product inventory in order to enable duplicates in the junction table. */
    @Id
    
    @Column(name = "product_inventory_id")
    private Long productInventoryId;

    /** The ID of the product associated with this inventory. */
    @Column(name = "product_id")
    private Long productId;

    /** The ID of the inventory associated with this product. */
    @Column(name = "inventory_id")
    private Long inventoryId;

    
}
