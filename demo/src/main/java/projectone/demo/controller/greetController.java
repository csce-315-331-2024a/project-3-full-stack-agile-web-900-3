package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/greet")
public class greetController {


    @GetMapping("/admin")
    public String greetAdmin(){
        return "adminGreet";
    }

}
