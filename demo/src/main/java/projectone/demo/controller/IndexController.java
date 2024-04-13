package projectone.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import projectone.demo.repository.ProductsRepository;


@RequestMapping(value = "/manager")   
@Controller
class ProductsController{
    
    private final ProductsRepository repository;
    
    ProductsController(ProductsRepository repository){
        this.repository = repository;
    }
    @GetMapping
    String products(Model model)
    {
       model.addAttribute("manager", this.repository.findAll());

        return "manager";
    }
    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        System.out.println("going to delete Product number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
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


