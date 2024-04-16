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
    private Long product_id;

    private String productname;

    private BigDecimal price;

    private String product_type;

    public Products(String productname, BigDecimal price) {
        this.productname = productname;
        this.price = price;
    }


    @Override
    public String toString() {
        return "Products{" +
                "productname='" + productname + '\'' +
                ", price=" + price +
                '}';
    }

    public String getProductname(){
        return productname;
    }
    //return price
    public BigDecimal getPrice(){
        return price;
    }
}
