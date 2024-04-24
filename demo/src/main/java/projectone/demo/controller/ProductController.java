package projectone.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

import projectone.demo.repository.ProductsRepository;

@RequestMapping(value = "/manager")   
@Controller
class ProductsController{
    
    private final ProductsRepository repository;
    // importing products repository
    ProductsController(ProductsRepository repository){
        this.repository = repository;
    }
    //populating model in code from database
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

    @PostMapping("/add")
    String add(@RequestParam("new-productsName")String name,@RequestParam("new-productsPrice")String price,@RequestParam("new-productsType")String type,Model model)
    {
        Long newId = this.repository.findMaxId() +1;
        BigDecimal bdFromString = new BigDecimal(price);
        Products newProduct = new Products(newId, name, bdFromString, type);
        
        System.out.println("adding "+ name);
        this.repository.save(newProduct);
        model.addAttribute("manager", this.repository.findAll());
        return "manager :: manager-list"; // in this one im sending back a Thymeleaf fragment
        
    }
    @PostMapping()
    String update( @RequestParam("new-name")String name,@RequestParam("new-price")String price,@RequestParam("new-item")String type,@RequestParam("id")String id,Model model) // the text in the RequestParam correlates to the values i send back from html
    {
    Products newProd = new Products(Long.parseLong(id), name, new BigDecimal(price), type);// new type  of whatever im editing or updating in this case product basically the row of the table
    this.repository.save(newProd);//saving it to the repository i made
    model.addAttribute("inventory", this.repository.findAll()); // updating the model in code by copying everything from databse
    System.err.println(name +" changed");
    return "redirect:/manager";// this is where im redirecting to ie the whole html file 
        
    }
    

}