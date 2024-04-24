package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import projectone.demo.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    
}
