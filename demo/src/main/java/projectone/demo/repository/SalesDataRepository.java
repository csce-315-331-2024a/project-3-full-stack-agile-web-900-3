package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectone.demo.model.SalesData;

import java.util.List;

public interface SalesDataRepository extends JpaRepository<SalesData, Integer> {
      // You can define custom queries here if needed
      @Query(value = "SELECT " +
                  "p.product_id AS productId, " + 
                  "p.product_name AS productName, " + 
                  "COUNT(op.product_id) AS quantitySold, " + 
                  "SUM(p.price * op.quantity) AS totalSales " + 
                  "FROM orders o " + 
                  "JOIN order_products op ON o.order_id = op.order_id " + 
                  "JOIN products p ON op.product_id = p.product_id " + 
                  "WHERE o.order_datetime BETWEEN :start_time AND :end_time " +
                  "GROUP BY p.product_id, p.product_name " + 
                  "ORDER BY totalSales DESC", nativeQuery = true)
      List<SalesData> fetchSalesData(@Param("start_time") String startTime, @Param("end_time") String endTime);
}
