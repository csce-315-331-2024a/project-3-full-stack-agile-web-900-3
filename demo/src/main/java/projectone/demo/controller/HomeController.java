package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class HomeController {
    
    @RequestMapping("/start")
    public String start()
    {
        return "index";
    }
    

}
