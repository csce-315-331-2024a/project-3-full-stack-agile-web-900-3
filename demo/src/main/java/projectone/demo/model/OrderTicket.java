package projectone.demo.model;

import lombok.*;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 * @author Kaghan Odom
 */
/**
 * Entity representing the order_ticket table which stores the full ticket for an order
 * This entity links orders with the products they contain, including quantities of each product.
 */
@Entity(name = "OrderTicket")
@Table(name = "order_ticket")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderTicket implements Serializable {
    /**
     * The identifier for the product. This ID links to the Products table.
     */
    @Id
    private Long order_id;

    /**
     * The full ticket for the order describing the details of its contents
     */
    @Column(name = "ticket")
    private String ticket;

    /**
     * Provides a string representation of the order, including its ID, price, and datetime,
     * formatted to a human-readable form.
     * This is particularly useful for logging and debugging purposes.
     *
     * @return a string representation of the order ticket details.
     */
    @Override
    public String toString() {
        return "order_ticket{" +
                "order_id=" + order_id +
                ", ticket=" + ticket +
                '}';
    }
}
