package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectone.demo.model.Users;
import projectone.demo.repository.UsersRepository;

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

}
