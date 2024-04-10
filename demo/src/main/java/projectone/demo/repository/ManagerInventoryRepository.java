package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import projectone.demo.model.Inventory;


@Repository
public interface ManagerInventoryRepository extends JpaRepository<Inventory, Long> {
}
