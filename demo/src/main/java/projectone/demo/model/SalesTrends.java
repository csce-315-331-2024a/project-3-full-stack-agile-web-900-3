/**
 * Represents sales trends data retrieved from the database.
 */

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

/**
 * Entity class representing sales trends data stored in the database.
 */

@Entity(name="SalesTrends")
@Table(name="sales_trends")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesTrends implements Serializable {

    /** The unique numerical identifier for the sales trend data. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Added an ID for JPA identity management.

    /** The name of the first product to be viewed in the sales trend data. */
    @Column(name = "product_name_one", nullable = false)
    private String productNameOne;

    /** The name of the second product to be viewed the sales trend data. */
    @Column(name = "product_name_two", nullable = false)
    private String productNameTwo;

    /** The frequency of occurrence of the sales trend. */
    @Column(name = "frequency", nullable = false)
    private int freq;

    // @Override
    // public String toString() {
    //     return "SalesTrends{" +
    //             "id=" + id +
    //             ", productNameOne='" + productNameOne + '\'' +
    //             ", productNameTwo='" + productNameTwo + '\'' +
    //             ", frequency=" + freq +
    //             '}';
    // }
}