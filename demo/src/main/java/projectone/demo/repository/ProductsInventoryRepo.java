package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projectone.demo.model.ProductInventory;

@Repository
public interface ProductsInventoryRepo extends JpaRepository<ProductInventory, Long>{

    
}