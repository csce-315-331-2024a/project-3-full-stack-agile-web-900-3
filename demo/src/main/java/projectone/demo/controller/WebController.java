package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    

    @GetMapping("/")
    public String Index() {
        return "index"; // Render home.html
    }

    @GetMapping("/manager")
    public String Manager() {
        return "manager"; // Render about.html
    }

    @GetMapping("/cashierPage")
    public String cashierPage()
    {
        return "cashierPage";
    }

    @GetMapping("/menuBoard")
    public String menuBoard()
    {
        return "menuBoard";
    }


    @GetMapping("/pinpad")
    public String pinPad()
    {
        return "pinpad";
    }


}