package projectone.demo.repository;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projectone.demo.model.OrderProducts;
/**
 * Repository interface for accessing order products data.
 */
@Repository
public interface OrderProductsRepo extends JpaRepository<OrderProducts,Long>
{
           /**
     * Finds order products within a specific range of order IDs.
     * 
     * @param startOrderId the starting order ID
     * @param endOrderId the ending order ID
     * @return a list of order products within the specified ID range
     */
      @Query("SELECT op FROM OrderProducts op WHERE op.order_id BETWEEN :startOrderId AND :endOrderId ORDER BY op.order_id")
    ArrayList<OrderProducts> findOrderProductsByOrderIdBetween(
        @Param("startOrderId") Long startOrderId,
        @Param("endOrderId") Long endOrderId
        );
         /**
     * Retrieves the most recently placed order.
     * 
     * @return a list containing the last order
     */
        @Query(value = "SELECT * FROM order_products ORDER BY order_id DESC LIMIT 1",nativeQuery = true)
        List<OrderProducts> getLastOrder();
          /**
     * Retrieves the maximum order product ID from the order_products table.
     * 
     * @return the maximum order product ID
     */
        @Query(value = "SELECT MAX(order_product_id) FROM order_products", nativeQuery = true)
        Long findMaxId(); 
   /**
     * Finds product IDs associated with a specific order ID.
     * 
     * @param orderId the order ID to search for
     * @return a list of product IDs associated with the given order ID
     */
        @Query("SELECT op.product_id FROM OrderProducts op WHERE op.order_id = :orderId")
        List<Long> findProductIdsByOrderId(@Param("orderId") Long orderId);
        /**
     * Finds order products by order ID and product ID.
     * 
     * @param orderId the order ID
     * @param productId the product ID
     * @return a list of order products matching both order ID and product ID
     */
        @Query("SELECT op FROM OrderProducts op WHERE op.order_id = :orderId AND op.product_id = :productId ORDER BY op.order_id")
        List<OrderProducts> findByOrderIdAndProductId(
        @Param("orderId") Long orderId,
        @Param("productId") Long productId
    );
       /**
     * Deletes all order products associated with a given order ID.
     * 
     * @param orderId the order ID whose associated products should be deleted
     */
     @Modifying
    @Transactional
    @Query("DELETE FROM OrderProducts op WHERE op.order_id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);


        
        
        

   
}