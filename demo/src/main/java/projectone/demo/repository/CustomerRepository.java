package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Products, Long> {
    @Query(value = "SELECT MAX(product_id) FROM products", nativeQuery = true)
    Long findMaxId(); 

    List<Products> findByProductType(String productType);
}