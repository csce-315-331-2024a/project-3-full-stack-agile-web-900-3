package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    // You can define custom queries here if needed
    
}
