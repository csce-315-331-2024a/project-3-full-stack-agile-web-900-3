package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "product_inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInventory implements Serializable {

    @Id
    
    @Column(name = "product_inventory_id")
    private Long productInventoryId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "inventory_id")
    private Long inventoryId;

    
}
