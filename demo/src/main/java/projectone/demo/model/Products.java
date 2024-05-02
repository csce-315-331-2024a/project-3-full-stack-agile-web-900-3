/**
 * Represents the products table in the system.
 */
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

/**
 * Entity class representing products available in the system.
 */

@Entity(name="Products")
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products implements Serializable{
    /** The unique numerical identifier for the product. */
    @Id
    private Long product_id;

    /** The name of the product. */
    private String productname;

    /** The price of the product. */
    private BigDecimal price;


    /** The type of the product. */
    // map our product_type attribute column to the productType string
    @Column(name = "product_type")
    private String productType;

    /**
     * Constructor for creating a product with specified name, price, and type.
     * @param productname The name of the product.
     * @param price The price of the product.
     * @param product_type The type of the product.
    */

    public Products(String productname, BigDecimal price, String product_type) {
        this.productname = productname;
        this.price = price;
        this.productType = product_type;
    }


    @Override
    public String toString() {
        return "Products{" +
                "product_id=" + product_id +
                ", productname='" + productname + '\'' +
                ", price=" + price +
                ", product_type='" + productType + '\'' +
                '}';
    }

    /**
     * Retrieves the name of the product.
     * @return The name of the product.
     */

    public String getProductname(){
        return productname;
    }

    /**
     * Retrieves the price of the product.
     * @return The price of the product.
     */

    //return price
    public BigDecimal getPrice(){
        return price;
    }

    /**
     * Retrieves the type of the product.
     * @return The type of the product.
     */

    public String getProduct_type() {
        return productType;
    }
}
