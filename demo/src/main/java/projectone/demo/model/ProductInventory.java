package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity(name="ProductInventory")
@Table(name="product_inventory")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductInventory implements  Serializable{

    @Id
    private Long product_id;

    private Long inventory_id;
    
}
