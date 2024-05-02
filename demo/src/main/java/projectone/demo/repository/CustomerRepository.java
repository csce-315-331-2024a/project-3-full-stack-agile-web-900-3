package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;
import java.util.List;
/**
     * Retrieves the highest product ID from the products table.
     * 
     * 
     */
@Repository
public interface CustomerRepository extends JpaRepository<Products, Long> {
    
    @Query(value = "SELECT MAX(product_id) FROM products", nativeQuery = true)
    Long findMaxId(); 
       /**
     * Finds products by their type.
     * 
     * @param productType the type of products to find
     * @return a list of products matching the specified type
     */
    List<Products> findByProductType(String productType);
}