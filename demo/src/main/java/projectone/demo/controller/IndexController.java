package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import projectone.demo.model.Inventory;
import projectone.demo.model.Products;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.repository.ProductsRepository;







    @Controller // no logic in this this is purely mappping the index html to the javascript
    class IndexController
    {
    
        @GetMapping("/")
        public String Index() {
            return "index"; 
        }

        @GetMapping("/check")
        public void checkDetails() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            OidcUser principal = (OidcUser) authentication.getPrincipal();

            String username = principal.getName();
            Map<String, Object> attributes = principal.getAttributes();

            System.out.println("Username: " + username);
            System.out.println("Details: " + attributes);
        }
    
    }
    

    @RequestMapping(value = "/cashierPage")
    @Controller // no logic in this this is purely mappping the index html to the javascript
    class CashierController{
        private final ProductsRepository repository;
        CashierController(ProductsRepository repository)
        {
            this.repository = repository;
        }
    @GetMapping
    String products(Model model)
    {
        model.addAttribute("products", this.repository.findAll());
        return "cashierPage";
    }
    }
    
    
//    @Controller // no logic in this this is purely mappping the index html to the javascript
//    class MenuBoardController
//    {
//        @GetMapping("/menuBoard")
//    public String menuBoard()
//    {
//        return "menuBoard";
//    }
//    }
    
    
    @Controller
    class PinPadController
    {
        @GetMapping("/pinpad")
    public String pinPad()
    {
        return "pinpad";
    }
}


