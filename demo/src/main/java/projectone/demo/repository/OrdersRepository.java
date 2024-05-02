package projectone.demo.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Orders;
/**
 * repository for our orders table
 */
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // You can define custom queries here if needed
    /**
     * Finds orders that are currently in 'processing' status.
     * 
     * @return a list of orders with the status 'processing'
     */
    @Query(value = "SELECT * FROM orders WHERE order_status = 'processing' ORDER BY order_id ASC", nativeQuery = true)
    List<Orders> findOrdersWithStatusProcessing();
    /**
     * Retrieves the highest order ID from the orders table.
     * 
     * @return the maximum order ID
     */
    @Query(value = "SELECT MAX(order_id) FROM orders", nativeQuery = true)
    Long findMaxId(); 
        /**
     * Finds orders within a specified date range.
     * 
     * @param startDate the start of the date range
     * @param endDate the end of the date range
     * @return a list of orders within the specified date range
     */
    @Query("SELECT o FROM Orders o WHERE o.orderDatetime BETWEEN :startDate AND :endDate ORDER BY o.orderDatetime")
    ArrayList<Orders> findOrdersWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);
     /**
     * Finds orders within a specified date range, sorted by order ID.
     * 
     * @param startDate the start of the date range
     * @param endDate the end of the date range
     * @return a list of orders sorted by order ID within the specified date range
     */
    @Query("SELECT o FROM Orders o WHERE o.orderDatetime BETWEEN :startDate AND :endDate ORDER BY o.order_id ASC")
    ArrayList<Orders> findOrdersWithinDateRangeSorted(LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Retrieves the most recently placed order.
     * 
     * @return a list containing the last order
     */
    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1",nativeQuery = true)
    List<Orders> getLastOrder();
}
