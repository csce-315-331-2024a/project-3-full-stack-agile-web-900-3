package projectone.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Inventory;
import projectone.demo.projection.InventoryUsageStatistic;
import projectone.demo.projection.OverstockProjection;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You can define custom queries here if needed
    @Query(value = "SELECT MAX(id) FROM inventory", nativeQuery = true)
    Long findMaxId(); 
    
    @Query(value = "SELECT " +
    "i.id AS inventoryId, " +
    "i.name AS inventoryName, " +
    "i.type AS inventoryType, " +
    "i.quantity AS currentQuantity, " +
    "i.unit AS unit, " +
    "i.low_threshold AS threshold, " +
    "COALESCE(op.usage_count, 0) AS usageCount " +
    "FROM " +
        "inventory i " +
    "LEFT JOIN ( " +
        "SELECT " +
            "pi.inventory_id, " +
            "COUNT(*) AS usage_count " +
        "FROM " +
            "order_products op " +
        "JOIN " +
            "orders o ON op.order_id = o.order_id " +
        "JOIN " +
            "product_inventory pi ON op.product_id = pi.product_id " +
        "WHERE " +
            "o.order_datetime BETWEEN :start_time AND :end_time " +
        "GROUP BY " +
            "pi.inventory_id " +
    ") op ON i.id = op.inventory_id " +
    "WHERE " +
        "COALESCE(op.usage_count, 0) < i.low_threshold " +
    "ORDER BY " +
        "usage_count", nativeQuery = true)
    List<OverstockProjection> findOverstock(@Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime);
    
    @Query(value = "SELECT " +
           "i.name AS itemName, " + 
           "SUM(i.quantity) AS quantityUsed, " + 
           "o.order_datetime AS usageDate " +
           "FROM orders o " +
           "JOIN order_products op ON o.order_id = op.order_id " +
           "JOIN product_inventory pi ON op.product_id = pi.product_id " +
           "JOIN inventory i ON pi.inventory_id = i.id " +
           "WHERE o.order_datetime BETWEEN :start_time AND :end_time " +
           "GROUP BY o.order_datetime, i.name " +
           "ORDER BY o.order_datetime",
           nativeQuery = true)
    List<Object[]> findDailyInventoryUsageData(@Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime);
}
