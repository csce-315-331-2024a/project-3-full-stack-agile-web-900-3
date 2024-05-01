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

@Entity(name="Products")
@Table(name="products")
@NoArgsConstructor
@Getter
@Setter
public class Products implements Serializable{
    @Id
    private Long product_id;

    private String productname;

    private BigDecimal price;

    // map our product_type attribute column to the productType string
    @Column(name = "product_type")
    private String productType;

    public Products(Long product_id,String productname, BigDecimal price, String product_type) {
        this.product_id = product_id;
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

    public Long getProductID() {
        return product_id;
    }

    public String getProductname(){
        return productname;
    }
    //return price
    public BigDecimal getPrice(){
        return price;
    }

    public String getProduct_type() {
        return productType;
    }
}
