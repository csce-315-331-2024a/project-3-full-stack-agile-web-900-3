package projectone.demo.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // You can define custom queries here if needed
    @Query(value = "SELECT * FROM orders WHERE order_status = 'processing' ORDER BY order_id ASC", nativeQuery = true)
    List<Orders> findOrdersWithStatusProcessing();
    @Query(value = "SELECT MAX(order_id) FROM orders", nativeQuery = true)
    Long findMaxId(); 

    @Query("SELECT o FROM Orders o WHERE o.orderDatetime BETWEEN :startDate AND :endDate ORDER BY o.orderDatetime")
    ArrayList<Orders> findOrdersWithinDateRange(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT o FROM Orders o WHERE o.orderDatetime BETWEEN :startDate AND :endDate ORDER BY o.order_id ASC")
    ArrayList<Orders> findOrdersWithinDateRangeSorted(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "SELECT * FROM orders ORDER BY order_id DESC LIMIT 1",nativeQuery = true)
    List<Orders> getLastOrder();
}
