package projectone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import projectone.demo.model.OrderProducts;
import projectone.demo.model.Orders;
import projectone.demo.model.OrderTicket;;
/**
 * @author Kaghan Odom
 */
/**
 * Repository interface for accessing order ticket data.
 * This interface extends JpaRepository to inherit basic CRUD operations provided by Spring Data JPA.
 * It provides a method to retrieve ticket information based on the order ID.
 */

public interface OrderTicketRepository extends JpaRepository<OrderTicket,Long>{

    /**
     * Retrieves the ticket associated with the specified order ID.
     *
     * @param orderId the ID of the order for which to retrieve the ticket
     * @return a list of ticket(s) associated with the specified order ID
     */
    @Query(value = "SELECT ticket FROM order_ticket WHERE order_id = :order_id", nativeQuery = true)
    List<String> findTicketByOrderId(@Param("order_id") Long orderId);

    /**
     * Retrieves the most recently placed order.
     * 
     * @return a list containing the last order
     */
    @Query(value = "SELECT * FROM order_ticket ORDER BY order_id DESC LIMIT 1",nativeQuery = true)
    List<OrderTicket> getLastOrder();


}
