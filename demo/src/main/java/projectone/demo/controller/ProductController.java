package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.math.BigDecimal;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import projectone.demo.model.ProductInventory;
import projectone.demo.model.Products;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.repository.ProductInventoryRepository;
import projectone.demo.repository.ProductsRepository;

@RequestMapping(value = "Manager/manager")   
@Controller
class ProductsController{
    private final ProductInventoryRepository repositoryJunction;
    private final ProductsRepository repository;
    private final InventoryRepository invRepository;
    // importing products repository
    ProductsController(ProductsRepository repository,ProductInventoryRepository repositoryJunction,InventoryRepository invRepository){
        this.repository = repository;
        this.repositoryJunction = repositoryJunction;
        this.invRepository = invRepository;
    }
    //populating model in code from database
    @GetMapping
    String products(Model model,Model modelJunc,Model modelinventory )
    {
       model.addAttribute("manager", this.repository.findAll());
       modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
       modelinventory.addAttribute("inventory", this.invRepository.findAll());
        return "Manager/manager";
    }
    
    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        repositoryJunction.deleteAllByProductId(id);
        System.out.println("going to delete Product number: "+id);
        repository.deleteById(id);// this is a query from jpa repository
        return "";
    }

    @PostMapping("/add")
    String add(@RequestParam("new-productsName")String name,@RequestParam("new-productsPrice")String price,@RequestParam("new-productsType")String type,Model model)
    {
        Long newId = this.repository.findMaxId() +1;
        BigDecimal bdFromString = new BigDecimal(price);
        Products newProduct = new Products(newId, name, bdFromString, type);
        
        System.out.println("adding "+ name);
        this.repository.save(newProduct);
        model.addAttribute("manager", this.repository.findAll());
        return "Manager/manager :: manager-list"; // in this one im sending back a Thymeleaf fragment
        
    }
    @PostMapping("/addInv")
    String addInv(@RequestParam("new-inv-id")String invId,@RequestParam("id")String prodId,Model modelJunc)
    {
        Long newId = this.repositoryJunction.findMaxId() +1;
        ProductInventory newJunc = new ProductInventory(newId, Long.parseLong(prodId), Long.parseLong(invId));
        System.out.println("adding new junction");
        this.repositoryJunction.save(newJunc);
        modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
        return "redirect:/Manager/manager"; // in this one im sending back a Thymeleaf fragment
        
    }
    @PostMapping("/removeInv")
    String removeInv(@RequestParam("remove")String remove,Model modelJunc)
    {
        System.out.println(remove);
        repositoryJunction.deleteById(Long.parseLong(remove));
        modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
        return "redirect:/Manager/manager"; // in this one im sending back a Thymeleaf fragment
        
    }
    @PostMapping()
    String update( @RequestParam("new-name")String name,@RequestParam("new-price")String price,@RequestParam("new-item")String type,@RequestParam("id")String id,Model model) // the text in the RequestParam correlates to the values i send back from html
    {
    Products newProd = new Products(Long.parseLong(id), name, new BigDecimal(price), type);// new type  of whatever im editing or updating in this case product basically the row of the table
    this.repository.save(newProd);//saving it to the repository i made
    model.addAttribute("inventory", this.repository.findAll()); // updating the model in code by copying everything from databse
    System.err.println(name +" changed");
    return "redirect:/Manager/manager";// this is where im redirecting to ie the whole html file 
        
    }
    

}