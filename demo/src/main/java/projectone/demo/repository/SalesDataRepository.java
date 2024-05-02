package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectone.demo.model.SalesData;
import projectone.demo.projection.SalesDataProjection;

import java.sql.Timestamp;
import java.util.List;
/**
 * repostiory to get the sales data reports
 */
@Repository
public interface SalesDataRepository extends JpaRepository<SalesData, Integer> {
          /**
 * Retrieves sales data within the specified time range.
 *
 * @param startTime the beginning of the time range
 * @param endTime the end of the time range
 * @return a list of sales data projections
 */
      @Query(value = "SELECT " +
           "p.product_id AS productId, " +
           "p.productname AS productName, " +
           "COUNT(op.product_id) AS quantitySold, " +
           "SUM(p.price * op.quantity) AS totalSales " +
           "FROM orders o " +
           "JOIN order_products op ON o.order_id = op.order_id " +
           "JOIN products p ON op.product_id = p.product_id " +
           "WHERE o.order_datetime BETWEEN :start_time AND :end_time " +
           "GROUP BY p.product_id, p.productname " +
           "ORDER BY totalSales DESC", nativeQuery = true)
    List<SalesDataProjection> fetchSalesData(@Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime);

/**
 * Retrieves raw sales data for debugging purposes within a fixed time range
 * from January 1, 2023, to January 1, 2024.
 *
 * @return a list of objects arrays representing sales data
 */
      @Query(value = "SELECT " +
      "p.product_id AS productId, " + // Ensure the alias matches exactly with the entity field
      "p.productname AS productName, " + // Adjusted to match the database column name exactly
      "COUNT(op.product_id) AS quantitySold, " + // Alias must match the entity field
      "SUM(p.price * op.quantity) AS totalSales " + // Alias must match the entity field
      "FROM orders o " +
      "JOIN order_products op ON o.order_id = op.order_id " +
      "JOIN products p ON op.product_id = p.product_id " +
      "WHERE o.order_datetime BETWEEN '2023-01-01 01:01:01' AND '2024-01-01 01:01:01' " +
      "GROUP BY p.product_id, p.productname " + // Adjusted to match the database column name exactly
      "ORDER BY totalSales DESC", nativeQuery = true)
      List<Object[]> fetchSalesDataDebug();
}