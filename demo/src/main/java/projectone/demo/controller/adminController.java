package projectone.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import projectone.demo.model.Users;
import projectone.demo.repository.UsersRepository;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class adminController {

    private UsersRepository usersRepository;

    public adminController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @GetMapping
    public String getadmin(Model model){
        model.addAttribute("UserData", usersRepository.findAll());

        return "admin";
    }


    @PostMapping("/add")
    public String adduser(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("role") String role, Model model){
        System.out.println(name + " " + email + " " + role);
        usersRepository.save(new Users(name, email, role));

        model.addAttribute("UserData", usersRepository.findAll());
        return "fragments/UserDatafrag";
    }

    @GetMapping("/delete")
    public String updateUser(@RequestParam("user_id") String userId, Model model) {
        System.out.println("deleting user with ID: " + userId);

        usersRepository.deleteById(Long.parseLong(userId));

        model.addAttribute("UserData", usersRepository.findAll());
        return "fragments/UserDatafrag";
    }

}
