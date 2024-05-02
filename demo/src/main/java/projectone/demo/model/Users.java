/**
 * Represents a user entity in the system.
 */
package projectone.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing users in the system.
 */
@Entity(name="Users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {

    /** The unique identifier for the user. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    /** The name of the user. */
    private String name;

    /** The email of the user. */
    private String email;

    /** The role of the user. */
    private String role;

    /**
     * Constructor for creating a user with specified name, email, and role.
     * @param name The name of the user.
     * @param email The email of the user.
     * @param role The role of the user.
     */
    public Users(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Retrieves the user ID.
     * @return The user ID.
     */
    public Long getUser_id() {
        return user_id;
    }

    /**
     * Retrieves the name of the user.
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the email of the user.
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the role of the user.
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Generates a string representation of the user's data.
     * @return A string representation of the user.
     */
    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
