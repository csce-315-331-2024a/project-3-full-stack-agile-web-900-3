package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author Koby kuruvilla
 */
/**
 * Controller for handling OAuth2 authentication with Google.
 */
@Controller
@RequestMapping(value = "/login/oauth2/code/google")

public class authentication {
        /**
     * Handles the GET request for Google OAuth2 login.
     * 
     * @return the login view directory
     */
    @GetMapping
    public String getLogin() {
        System.out.println("get mapping made it here");
        return "login";
    }
        /**
     * Handles the POST request for Google OAuth2 login.
     * 
     * @return the login view directory
     */
    @PostMapping
    public String postLogin() {
        System.out.println("post mapping made it here");
        return "login";
    }


}
