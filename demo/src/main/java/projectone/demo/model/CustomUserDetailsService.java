package projectone.demo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projectone.demo.model.Users;
import projectone.demo.repository.UsersRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author Koby Kruvulla
 */
/**
 * Service to bridge between Spring Security's {@link UserDetailsService} and
 * our application-specific user model and persistence methods.
 * This service is responsible for retrieving user details from the database
 * based on the username (email in this case).
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
        /**
     * Locates the user based on the username. In this implementation, the username
     * is actually the email of the user. It fetches the user's details from the
     * database and builds a {@link UserDetails} object for authentication purposes.
     *
     * @param username the username identifying the user whose data is required,
     *                 which is an email for this application.
     * @return a fully populated {@link UserDetails} record (never {@code null}).
     * @throws UsernameNotFoundException if the user could not be found or the user has no GrantedAuthority.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        Users user = usersRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(user.getRole().equals("admin"))
        {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(user.getEmail(), "", authorities);
    }
}