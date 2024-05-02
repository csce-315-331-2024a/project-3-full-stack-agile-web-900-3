package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectone.demo.model.SalesTrends;
import projectone.demo.projection.SalesTrendsProjection;

import java.sql.Timestamp;
import java.util.List;

public interface SalesTrendsRepository extends JpaRepository<SalesTrends, Long> {
    @Query(value = "SELECT " +
            "p1.productname AS productNameOne, " +
            "p2.productname AS productNameTwo, " +
            "COUNT(*) AS freq " +
            "FROM " +
            "order_products op1 " +
            "JOIN order_products op2 ON op1.order_id = op2.order_id AND op1.product_id < op2.product_id " +
            "JOIN orders o ON op1.order_id = o.order_id " +
            "JOIN products p1 ON op1.product_id = p1.product_id " +
            "JOIN products p2 ON op2.product_id = p2.product_id " +
            "WHERE o.order_datetime BETWEEN :start_time AND :end_time " +
            "GROUP BY p1.productname, p2.productname " +
            "ORDER BY freq DESC ", nativeQuery = true)
    List<SalesTrendsProjection> findSalesTrends(@Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime);
}

