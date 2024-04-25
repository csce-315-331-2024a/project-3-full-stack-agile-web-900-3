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

    @PostMapping()
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