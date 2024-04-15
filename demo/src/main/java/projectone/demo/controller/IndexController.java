package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    String add(@RequestParam("new-productsName")String name,@RequestParam("new-productsPrice")String price,@RequestParam("new-productsType")String type,Model model)
    {
        BigDecimal bdFromString = new BigDecimal(price);
        Products newProduct = new Products(null, name, bdFromString, type);
        
        System.out.println("adding "+ name);
        this.repository.save(newProduct);
        model.addAttribute("manager", this.repository.findAll());
        return "manager :: manager-list";
        
    }
    

}
@RequestMapping(value = "/inventory")   
@Controller
class InventoryController{
    private final InventoryRepository repository;

    InventoryController(InventoryRepository repository)
    {
        this.repository = repository;
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "inventory";
    }
}
// @RequestMapping(value = "/inventory-manager")   
// @Controller
// class InventoryController
// {
//      private final InventoryRepository repository;

//      InventoryController(InventoryRepository repository)
//      {
//         this.repository = repository;
//      }

//      @GetMapping
//      String ManagerPage(Model model)
//      {
//         model.addAttribute("inventory", this.repository.findAll());
//         return "manager";
//      }
    //  @PostMapping("/addInventory")
    // public String addInventory(@RequestParam("name") String name, 
    //                         @RequestParam("type") String type, 
    //                         @RequestParam("quantity") Integer quantity,
    //                         @RequestParam("unit") String unit,
    //                         @RequestParam("lowThreshold") Integer lowThreshold,
    //                         @RequestParam("orderDate") String orderDate,
    //                         Model model) {
    //     LocalDate date = LocalDate.parse(orderDate);
    //     Inventory newInventory = new Inventory(null, name, type, quantity, unit, lowThreshold, date);
    //     repository.save(newInventory);
    //     return "redirect:/manager";  // Redirect to manage page
    // }

    // @DeleteMapping("/deleteInventory/{id}")
    // @ResponseBody
    // public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
    //     try {
    //         repository.deleteById(id);
    //         return ResponseEntity.ok().build();
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    //     }
    // }

//}



    @Controller // no logic in this this is purely mappping the index html to the javascript
    class IndexController
    {
    
        @GetMapping("/")
        public String Index() {
            return "index"; 
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


