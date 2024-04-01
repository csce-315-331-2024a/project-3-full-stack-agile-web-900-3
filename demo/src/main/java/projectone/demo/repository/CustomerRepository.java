package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;

@Repository
public interface CustomerRepository extends JpaRepository<Products, Long> {
}


