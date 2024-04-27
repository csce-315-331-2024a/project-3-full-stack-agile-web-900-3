package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happy")
public class loginController {


    @GetMapping
    public String login() {
        System.out.println("gets here");
        return "redirect:/menuBoard";
    }

}
