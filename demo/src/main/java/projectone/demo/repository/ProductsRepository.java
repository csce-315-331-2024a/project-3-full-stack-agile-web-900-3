package projectone.demo.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> { // give it the product table as an attibute Jpa repository is a repo of queries
    @Query(value = "SELECT MAX(product_id) FROM products", nativeQuery = true)
    Long findMaxId(); 
}


