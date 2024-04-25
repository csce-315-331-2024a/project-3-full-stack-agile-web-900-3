package projectone.demo.model;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

import java.io.Serializable;

@Entity(name="SalesData")
@Table(name="sales_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesData implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "quantity_sold", nullable = false)
    private int quantitySold;

    @Column(name = "total_sales", nullable = false)
    private double totalSales;

    @Override
    public String toString() {
        return "SalesData{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantitySold=" + quantitySold +
                ", totalSales=" + totalSales +
                '}';
    }
}
