package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * @author Koby kuruvilla
 */
/**
 * handles the greeting for the different roles
 */
@Controller
@RequestMapping("/greet")
public class greetController {

    /**
     * Greets the admin.
     *
     * @return the admin greeting view directory
     */
    @GetMapping("/admin")
    public String greetAdmin(){
        return "adminGreet";
    }
    /**
     * Greets the manager.
     *
     * @return the manager greeting view directory
     */
    @GetMapping("/Manager/Inventory/food")
    public String greetManager(){
        return "managerGreet";
    }
     /**
     * Greets the cashier.
     *
     * @return the cashier greeting view directory
     */
    @GetMapping("/cashier")
    public String greetCashier(){
        return "cashierGreet";
    }
      /**
     * Greets the customer.
     *
     * @return the customer greeting view directory
     */
    @GetMapping("/customer")
    public String greetCustomer(){
        return "customerGreet";
    }

}
