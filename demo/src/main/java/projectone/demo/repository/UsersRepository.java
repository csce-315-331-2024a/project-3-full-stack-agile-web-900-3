package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;
import projectone.demo.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> { // give it the product table as an attibute Jpa repository is a repo of queries
    Users findByEmail(String email);

}
