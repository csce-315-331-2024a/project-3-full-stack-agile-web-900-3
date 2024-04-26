package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import projectone.demo.model.ProductInventory;

@Repository
public interface ProductsInventoryRepo extends JpaRepository<ProductInventory, Long>{
    @Query(value = "SELECT MAX(product_inventory_id) FROM product_inventory", nativeQuery = true)
    Long findMaxId(); 

    @Modifying
    @Transactional
    @Query("DELETE FROM ProductInventory p WHERE p.productId = :productId")
    void deleteAllByProductId(@Param("productId") Long productId);
    
}