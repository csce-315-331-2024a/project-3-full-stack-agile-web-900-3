package projectone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // You can define custom queries here if needed
    @Query(value = "SELECT * FROM orders WHERE order_status = 'processing'", nativeQuery = true)
    List<Orders> findOrdersWithStatusPending();
}
