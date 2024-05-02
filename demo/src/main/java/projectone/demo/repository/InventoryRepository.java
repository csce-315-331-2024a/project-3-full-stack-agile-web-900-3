package projectone.demo.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projectone.demo.model.Inventory;
import projectone.demo.projection.InventoryUsageStatistic;
import projectone.demo.projection.OverstockProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * Repository interface for inventory data access operations.
 */

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    /**
     * Retrieves the highest inventory ID from the inventory table.
     * 
     * @return the maximum inventory ID
     */
    // You can define custom queries here if needed
    @Query(value = "SELECT MAX(id) FROM inventory", nativeQuery = true)
    Long findMaxId(); 
      /**
     * Finds inventory items that are understocked within a specified time period.
     * 
     * @param startTime the start time of the period to consider
     * @param endTime the end time of the period to consider
     * @return a list of overstock projections for items with usage counts below their low threshold
     */
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

    @Query(value = "SELECT i.name AS itemName, SUM(op.quantity) AS quantityUsed " +
           "FROM orders o " +
           "JOIN order_products op ON o.order_id = op.order_id " +
           "JOIN product_inventory pi ON op.product_id = pi.product_id " +
           "JOIN inventory i ON pi.inventory_id = i.id " +
           "WHERE o.order_datetime BETWEEN :start_time AND :end_time " +
           "GROUP BY i.name",
           countQuery = "SELECT COUNT(DISTINCT i.id) FROM Inventory i",
           nativeQuery = true)
    Page<Object[]> findAggregatedInventoryUsage(@Param("start_time") Timestamp startTime, @Param("end_time") Timestamp endTime, Pageable pageable);

    @Query(value = "SELECT * FROM inventory WHERE quantity < low_threshold", nativeQuery = true)
    List<Inventory> findItemsBelowThreshold();

    @Transactional
    @Modifying
    @Query(value = "UPDATE inventory SET quantity = quantity - :quantityUsed WHERE id = :inventoryId", nativeQuery = true)
    void updateInventoryQuantity(@Param("inventoryId") Long inventoryId, @Param("quantityUsed") int quantityUsed);

}
