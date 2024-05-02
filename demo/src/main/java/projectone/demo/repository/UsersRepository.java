package projectone.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectone.demo.model.Products;
import projectone.demo.model.Users;
/**
 * repository for our user tables
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> { // give it the product table as an attibute Jpa repository is a repo of queries
    /**
 * Retrieves a user by their email address.
 *
 * @param email the email address of the user to retrieve
 * @return the user associated with the specified email address, or null if no user is found
 */
    Users findByEmail(String email);

}
