package projectone.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author Koby kuruvilla
 */
/**
 * Controller for handling logout and session reset.
 */
@Controller
@RequestMapping("/reset")
public class logout {

    @PostMapping
    public String reset(HttpServletRequest request){
        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        System.out.println("Logged out");
        return "redirect:/";
    }

}
