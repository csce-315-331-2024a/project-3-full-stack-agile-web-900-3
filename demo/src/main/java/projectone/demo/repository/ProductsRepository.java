package projectone.demo.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;
/**
 * Repository interface for accessing and managing product data.
 */
@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> { // give it the product table as an attibute Jpa repository is a repo of queries
      /**
     * Retrieves the highest product ID from the products table.
     * 
     * @return the maximum product ID
     */
    @Query(value = "SELECT MAX(product_id) FROM products", nativeQuery = true)
    Long findMaxId(); 
    
    /**
     * Retrieves all products, sorted by product type.
     * 
     * @return a list of all products, sorted by their type
     */
     @Query(value = " SELECT * FROM products ORDER BY product_type",nativeQuery = true)
    ArrayList<Products> getByProductType();
    

}


