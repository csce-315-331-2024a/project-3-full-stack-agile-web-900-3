package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import projectone.demo.repository.ProductsRepository;


    
@Controller
class ProductsController{
    
    private final ProductsRepository repository;
    
    ProductsController(ProductsRepository repository){
        this.repository = repository;
    }
    @GetMapping("/manager")
    String products(Model model)
    {
       model.addAttribute("manager", this.repository.findAll());

        return "manager";
    }

}
   

    @Controller // no logic in this this is purely mappping the index html to the javascript
    class IndexController
    {
    
        @GetMapping("/")
        public String Index() {
            return "index"; 
        }
    
    }
    
    @Controller // no logic in this this is purely mappping the index html to the javascript
    class CashierController{
        @GetMapping("/cashierPage")
    public String cashierPage()
    {
        return "cashierPage";
    }
    }
    
    
    @Controller // no logic in this this is purely mappping the index html to the javascript
    class MenuBoardController
    {
        @GetMapping("/menuBoard")
    public String menuBoard()
    {
        return "menuBoard";
    }
    }
    
    
    @Controller
    class PinPadController
    {
        @GetMapping("/pinpad")
    public String pinPad()
    {
        return "pinpad";
    }
}


