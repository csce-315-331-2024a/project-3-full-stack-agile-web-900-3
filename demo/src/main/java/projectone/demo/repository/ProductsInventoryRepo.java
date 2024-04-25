package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import projectone.demo.model.ProductInventory;

@Repository
public interface ProductsInventoryRepo extends JpaRepository<ProductInventory, Long>{
    @Query(value = "SELECT MAX(product_inventory_id) FROM product_inventory", nativeQuery = true)
    Long findMaxId(); 
    
}