package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // You can define custom queries here if needed
    @Query(value = "SELECT MAX(id) FROM inventory", nativeQuery = true)
    Long findMaxId(); 
}
