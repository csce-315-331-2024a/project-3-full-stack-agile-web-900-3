package projectone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projectone.demo.model.ProductInventory;
/**
 * Repository interface for accessing and managing product inventory data.
 */
@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long>{
        /**
     * Retrieves the highest product inventory ID from the product_inventory table.
     * 
     * @return the maximum product inventory ID
     */
    @Query(value = "SELECT MAX(product_inventory_id) FROM product_inventory", nativeQuery = true)
    Long findMaxId(); 
    
    /**
     * Deletes all product inventory entries associated with a specific product ID.
     * 
     * @param productId the product ID whose inventory entries should be deleted
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductInventory p WHERE p.productId = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);
     /**
     * Finds the names of ingredients associated with a specific product ID.
     * 
     * @param productId the product ID to search for
     * @return a list of ingredient names associated with the product
     */
    @Query(value = "SELECT i.name FROM inventory i INNER JOIN product_inventory pi ON i.id = pi.inventory_id WHERE pi.product_id = :productId", nativeQuery = true)
    List<String> findIngredientNamesByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT pi.inventory_id FROM product_inventory pi WHERE pi.product_id = :productId", nativeQuery = true)
    List<Long> getInventoryIdsForProduct(@Param("productId") Long productId);
}