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

@Entity(name="Products")
@Table(name="products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Products implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger product_id;

    private String productname;

    private BigDecimal price;

    private String product_type;

    public Products(String productname, BigDecimal price, String product_type) {
        this.productname = productname;
        this.price = price;
        this.product_type = product_type;
    }


    @Override
    public String toString() {
        return "Products{" +
                "product_id=" + product_id +
                ", productname='" + productname + '\'' +
                ", price=" + price +
                ", product_type='" + product_type + '\'' +
                '}';
    }

    public String getProductname(){
        return productname;
    }
    //return price
    public BigDecimal getPrice(){
        return price;
    }

    public String getProduct_type() {
        return product_type;
    }
}
