package projectone.demo.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import projectone.demo.model.OrderProducts;

@Repository
public interface OrderProductsRepo extends JpaRepository<OrderProducts,Long>
{
      @Query("SELECT op FROM OrderProducts op WHERE op.order_id BETWEEN :startOrderId AND :endOrderId ORDER BY op.order_id")
    ArrayList<OrderProducts> findOrderProductsByOrderIdBetween(
        @Param("startOrderId") Long startOrderId,
        @Param("endOrderId") Long endOrderId
        );
        @Query(value = "SELECT * FROM order_products ORDER BY order_id DESC LIMIT 1",nativeQuery = true)
        List<OrderProducts> getLastOrder();
        @Query(value = "SELECT MAX(order_product_id) FROM order_products", nativeQuery = true)
        Long findMaxId(); 
   
}