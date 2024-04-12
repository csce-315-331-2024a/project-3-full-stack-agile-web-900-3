package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Long> {
}


