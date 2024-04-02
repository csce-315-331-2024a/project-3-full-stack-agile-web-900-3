package projectone.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class CashierController {

    @GetMapping("/")
    public String index() {
        return "cashierPage.html"; // Return the name of your HTML file (index.html)
    }

    @PostMapping("/updateText")
    @ResponseBody
    public String updateText(@RequestParam String newText) {
        // Handle logic to update variables based on newText
        return newText;
    }
}
