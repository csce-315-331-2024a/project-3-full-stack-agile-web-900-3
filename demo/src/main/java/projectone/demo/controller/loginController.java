package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author Koby kuruvilla
 */
/**
 * Controller for handling login redirects.
 */
@Controller
@RequestMapping("/happy")
public class loginController {

     /**
     * Redirects to the menu board after login.
     *
     * @return redirect string to the menu board
     */
    @GetMapping
    public String login() {
        System.out.println("gets here");
        return "redirect:/menuBoard";
    }

}
