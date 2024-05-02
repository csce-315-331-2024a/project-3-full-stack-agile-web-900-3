/**
 * Represents sales data retrieved from the database.
 */
package projectone.demo.model;
import lombok.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ColumnResult;

import java.io.Serializable;


/**
 * Entity class representing sales data stored in the database.
 */

@SqlResultSetMapping(
    name = "SalesDataMapping",
    classes = @ConstructorResult(
        targetClass = SalesData.class,
        columns = {
            @ColumnResult(name = "productId", type = Integer.class),
            @ColumnResult(name = "productName", type = String.class),
            @ColumnResult(name = "quantitySold", type = Integer.class),
            @ColumnResult(name = "totalSales", type = Double.class)
        }
    )
)

@Entity(name="SalesData")
@Table(name="sales_data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SalesData implements Serializable {

    /** The unique identifier for the product in the sales data. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId") // Ensure this name is used as an alias in your SQL query
    private int productId;

     /** The name of the product in the sales data. */
    @Column(name = "productName", nullable = false) // Ensure this name is used as an alias in your SQL query
    private String productName;

    /** The quantity of the product sold. */
    @Column(name = "quantitySold", nullable = false) // Ensure this name is used as an alias in your SQL query
    private int quantitySold;

    /** The total sales amount of the product. */
    @Column(name = "totalSales", nullable = false) // Ensure this name is used as an alias in your SQL query
    private double totalSales;

    // @Override
    // public String toString() {
    //     return "SalesData{" +
    //             "productId=" + productId +
    //             ", productName='" + productName + '\'' +
    //             ", quantitySold=" + quantitySold +
    //             ", totalSales=" + totalSales +
    //             '}';
    // }
}
