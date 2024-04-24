package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;


@Entity(name="SalesTrends")
@Table(name="sales_trends")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesTrends implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Added an ID for JPA identity management.

    @Column(name = "product_name_one", nullable = false)
    private String productNameOne;

    @Column(name = "product_name_two", nullable = false)
    private String productNameTwo;

    @Column(name = "frequency", nullable = false)
    private int freq;

    @Override
    public String toString() {
        return "SalesTrends{" +
                "id=" + id +
                ", productNameOne='" + productNameOne + '\'' +
                ", productNameTwo='" + productNameTwo + '\'' +
                ", frequency=" + freq +
                '}';
    }
}