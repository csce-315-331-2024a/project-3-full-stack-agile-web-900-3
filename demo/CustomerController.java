package projectone.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.Date;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @GetMapping
    public String start(Model model)
    {
        model.addAttribute("now" , new Date().toInstant());
        model.addAttribute("item" , "test");
        return "customer";
    }

    @PostMapping
    public String 

    @DeleteMapping(path = "/delete", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String delete() {
        return "";
    }

    @PutMapping("/add")
    public String addMenuItem(@RequestParam("item-id") String item, Model model)
    {
        model.addAttribute("item", item);
        return "customer :: customer";
    }
}
