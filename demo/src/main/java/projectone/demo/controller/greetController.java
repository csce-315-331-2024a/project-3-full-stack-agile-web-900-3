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

    @GetMapping("/Manager/manager")
    public String greetManager(){
        return "managerGreet";
    }

    @GetMapping("/cashier")
    public String greetCashier(){
        return "cashierGreet";
    }

    @GetMapping("/customer")
    public String greetCustomer(){
        return "customerGreet";
    }

}
