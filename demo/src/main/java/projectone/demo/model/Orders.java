package projectone.demo.model;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
/**
 * Entity representing the orders table in the database.
 * This class includes information about the order such as its ID, price, date, and status.
 * It uses Lombok annotations to automatically generate getters, setters, a constructor with all arguments,
 * a no-argument constructor, and a customized toString method.
 */
@Entity(name="Orders")
@Table(name="orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders implements Serializable{
      /**
     * Unique identifier for the order. This field serves as the primary key in the database table.
     */
    @Id
    private Long order_id;
    /**
     * The total price of the order, stored as a {@link BigDecimal} to handle precise monetary calculations.
     */
    private BigDecimal price;
    /**
     * The current status of the order (e.g., processing, completed, canceled).
     * This field allows the system to track the progress and management of the order.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_datetime")
    private LocalDateTime orderDatetime;
      /**
     * The current status of the order (e.g., processing, completed, canceled).
     * This field allows the system to track the progress and management of the order.
     */
  
    @Column(name = "order_status")
    private String status;
        /**
     * Provides a string representation of the order, including its ID, price, and datetime,
     * formatted to a human-readable form.
     * This is particularly useful for logging and debugging purposes.
     *
     * @return a string representation of the order details.
     */
    @Override
    public String toString() {
        return "Orders{" +
                "order_id=" + order_id +
                ", price=" + price +
                ", orderDatetime=" + orderDatetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
