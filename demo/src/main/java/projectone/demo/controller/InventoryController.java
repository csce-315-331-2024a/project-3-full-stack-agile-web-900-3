package projectone.demo.controller;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projectone.demo.model.Inventory;
import projectone.demo.model.Products;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.repository.ProductsRepository;
@RequestMapping(value = "/inventory")   
@Controller
class InventoryController{
    @Autowired
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
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,Model model)
    {
       
        Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    inventoryItem.setOrderDate(date); 

    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    
    return "inventory";
        
    }
}
