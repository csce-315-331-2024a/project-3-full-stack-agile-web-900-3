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
/**
 * @author Quinn Bromley
 * 
 */
/**
 * Entity representation of the inventory table used to store inventory items details.
 * This class is annotated with JPA annotations to map it to the database table "inventory".
 * It uses Lombok annotations to automatically generate getters, setters, and constructors.
 */
@Entity(name="Inventory")
@Table(name="inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory implements Serializable {
    
    /**
     * Unique identifier for the Inventory item. It is marked with {@link Id} to indicate
     * that this field is the primary key. It is automatically generated by the database.
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * The name of the inventory item. This field cannot be null.
     */
    @Column(name = "name", nullable = false)
    private String name;
       /**
     * The type category of the inventory item. This field cannot be null.
     */
    @Column(name = "type", nullable = false)
    private String type;
     /**
     * The quantity of the inventory item in stock. This field cannot be null.
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
      /**
     * The unit of measurement for the inventory item quantity. This field cannot be null.
     */
    @Column(name = "unit", nullable = false)
    private String unit;
       /**
     * The low stock threshold. When inventory quantity falls below this value,
     * it indicates that the item is running low. This field cannot be null.
     */
    @Column(name = "low_threshold", nullable = false)
    private Integer lowThreshold;
    /**
     * The date on which the last order was made for this inventory item.
     * This field helps in tracking when the item was last ordered. This field cannot be null.
     */
    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;
}