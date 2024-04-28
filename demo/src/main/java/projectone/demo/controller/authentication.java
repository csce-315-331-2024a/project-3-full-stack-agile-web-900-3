package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login/oauth2/code/google")

public class authentication {

    @GetMapping
    public String getLogin() {
        System.out.println("get mapping made it here");
        return "login";
    }

    @PostMapping
    public String postLogin() {
        System.out.println("post mapping made it here");
        return "login";
    }


}
