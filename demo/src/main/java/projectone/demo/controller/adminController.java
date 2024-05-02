package projectone.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectone.demo.model.Users;
import projectone.demo.repository.UsersRepository;

import java.util.Map;
/**
 * @author Koby kuruvilla
 */
/**
 * Controller for administrative actions related to {@link Users}.
 */
@Controller
@RequestMapping("/admin")
public class adminController {

    private UsersRepository usersRepository;
     /**
     * Constructs an {@code AdminController} with the specified {@code UsersRepository}.
     * 
     * @param usersRepository the repository for user data access
     */
    public adminController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    /**
     * Displays the admin page with user data.
     * 
     * @param model the model to carry data to the view
     * @return the admin view directory
     */
    @GetMapping
    public String getadmin(Model model){
        model.addAttribute("UserData", usersRepository.findAll());

        return "admin";
    }

        /**
     * Adds a new user with the specified details and updates the model.
     * 
     * @param name the name of the user
     * @param email the email of the user
     * @param role the role of the user
     * @param model the model to carry data to the view
     * @return the fragment view directory for user data
     */
    @PostMapping("/add")
    public String adduser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("role") String role, Model model){
        System.out.println(name + " " + email + " " + role);
        usersRepository.save(new Users(name, email, role));

        model.addAttribute("UserData", usersRepository.findAll());
        return "fragments/UserDatafrag";
    }
    
    /**
     * Deletes the user with the specified ID and updates the model.
     * 
     * @param userId the ID of the user to delete
     * @param model the model to carry updated data to the view
     * @return the fragment view directory for user data
     */
    @GetMapping("/delete")
    public String updateUser(@RequestParam("user_id") String userId, Model model) {
        System.out.println("deleting user with ID: " + userId);

        usersRepository.deleteById(Long.parseLong(userId));

        model.addAttribute("UserData", usersRepository.findAll());
        return "fragments/UserDatafrag";
    }

}
