package projectone.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projectone.demo.model.ProductInventory;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long>{
    @Query(value = "SELECT MAX(product_inventory_id) FROM product_inventory", nativeQuery = true)
    Long findMaxId(); 

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductInventory p WHERE p.productId = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT i.name FROM inventory i INNER JOIN product_inventory pi ON i.id = pi.inventory_id WHERE pi.product_id = :productId", nativeQuery = true)
    List<String> findIngredientNamesByProductId(@Param("productId") Long productId);

    @Query(value = "SELECT pi.inventory_id FROM product_inventory pi WHERE pi.product_id = :productId", nativeQuery = true)
    List<Long> getInventoryIdsForProduct(@Param("productId") Long productId);
}