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
// @RequestMapping(value = "/inventory")   
// @Controller
// class InventoryController{
//     @Autowired
//     private InventoryRepository repository;

//     String InventoryController(InventoryRepository repository)
//     {
//         this.repository = repository;
//         return "inventory";
//     }
//     @GetMapping
//     String Inventory(Model model)
//     {
//         model.addAttribute("inventory", this.repository.findAll());

//         return "inventory";
//     }
//     @PostMapping
//     String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,Model model)
//     {
       
//         Long Id = Long.parseLong(id);
//     Integer amt = Integer.parseInt(amount);
//     LocalDate date = LocalDate.now();
    
//     Inventory inventoryItem = this.repository.getById(Id);
//      System.out.println(inventoryItem.toString());

//     inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
//     inventoryItem.setOrderDate(date); 

//     this.repository.save(inventoryItem);
//     model.addAttribute("inventory", this.repository.findAll());
    
//     return "redirect:/inventory";
        
//     }
//     String modify( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,Model model)
//     {
       
//     Long Id = Long.parseLong(id);
//     Integer amt = Integer.parseInt(amount);
//     LocalDate date = LocalDate.now();
    
//     Inventory inventoryItem = this.repository.getById(Id);
//      System.out.println(inventoryItem.toString());

//     inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
//     inventoryItem.setOrderDate(date); 

//     this.repository.save(inventoryItem);
//     model.addAttribute("inventory", this.repository.findAll());
    
//     return "inventory";
        
//     }
// }

@RequestMapping("/Inventory/food")
@Controller
class foodController
{
    @Autowired
    private  InventoryRepository repository;

    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Inventory/food";
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Inventory/food";
    }
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,@RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
      
    Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    Integer lowThresh = Integer.parseInt(low);
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    if(amt!=0)
    {
        inventoryItem.setOrderDate(date); 
    }
    
    inventoryItem.setName(name);
    inventoryItem.setLowThreshold(lowThresh);
    inventoryItem.setUnit(unit);


    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" changed");
    return "redirect:/Inventory/food";
        
    }


    @PostMapping("/add")
    String add( @RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
    Long id = this.repository.findMaxId();
    id+=1;
    System.out.println(id);
    LocalDate date = LocalDate.now();
    Inventory inventoryItem = new Inventory(id,name,"food",0,unit,Integer.parseInt(low),date);
     
    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" added");
    return "redirect:/Inventory/food";
        
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        try
        {
            System.out.println("going to delete Inventory  number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
        }
        catch(Exception e)
        {
            return handleException(id, e);
        }
    }

    public String handleException(Long id, Exception e) {
        // Log the exception details for debugging purposes
        System.err.println("An error occurred while processing request for ID: " + id);
        e.printStackTrace();

        // Return an HTML response indicating an error
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Error Message</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: rgba(0, 0, 0, 0.5); }" +
               ".popup { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 300px; padding: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); border-radius: 8px; background: #fff; z-index: 1000; }" +
               ".popup-content { text-align: center; }" +
               ".close-button { float: right; width: 1.5em; line-height: 1.5em; text-align: center; cursor: pointer; border-radius: 0.25em; background-color: lightgray; }" +
               ".close-button:hover { background-color: darkgray; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='popup'>" +
               "<div class='popup-content'>" +
               "<span class='close-button'>&times;</span>" +
               "<p>Error processing your request for Inventory ID " + id + ". ID not found or Item is used in a product, make sure no products use inventory item.</p>" +
               "</div>" +
               "</div>" +
               "<script>" +
               "document.querySelector('.close-button').addEventListener('click', function() {" +
               "document.querySelector('.popup').style.display = 'none';" +
               "});" +
               "</script>" +
               "</body>" +
               "</html>";
    }
}
    



@RequestMapping("Inventory/desert")
@Controller
class desertController
{
    @Autowired
    private  InventoryRepository repository;

    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Inventory/desert";
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Inventory/desert";
    }
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,@RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
      
    Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    Integer lowThresh = Integer.parseInt(low);
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    if(amt!=0)
    {
        inventoryItem.setOrderDate(date); 
    }
    
    inventoryItem.setName(name);
    inventoryItem.setLowThreshold(lowThresh);
    inventoryItem.setUnit(unit);


    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" changed");
    return "redirect:/Inventory/desert";
        
    }


    @PostMapping("/add")
    String add( @RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
    Long id = this.repository.findMaxId();
    id+=1;
    System.out.println(id);
    LocalDate date = LocalDate.now();
    Inventory inventoryItem = new Inventory(id,name,"desert",0,unit,Integer.parseInt(low),date);
     
    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" added");
    return "redirect:/Inventory/desert";
        
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        try
        {
            System.out.println("going to delete Inventory  number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
        }
        catch(Exception e)
        {
            return handleException(id, e);
        }
    }

    public String handleException(Long id, Exception e) {
        // Log the exception details for debugging purposes
        System.err.println("An error occurred while processing request for ID: " + id);
        e.printStackTrace();

        // Return an HTML response indicating an error
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Error Message</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: rgba(0, 0, 0, 0.5); }" +
               ".popup { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 300px; padding: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); border-radius: 8px; background: #fff; z-index: 1000; }" +
               ".popup-content { text-align: center; }" +
               ".close-button { float: right; width: 1.5em; line-height: 1.5em; text-align: center; cursor: pointer; border-radius: 0.25em; background-color: lightgray; }" +
               ".close-button:hover { background-color: darkgray; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='popup'>" +
               "<div class='popup-content'>" +
               "<span class='close-button'>&times;</span>" +
               "<p>Error processing your request for Inventory ID " + id + ". ID not found or Item is used in a product, make sure no products use inventory item.</p>" +
               "</div>" +
               "</div>" +
               "<script>" +
               "document.querySelector('.close-button').addEventListener('click', function() {" +
               "document.querySelector('.popup').style.display = 'none';" +
               "});" +
               "</script>" +
               "</body>" +
               "</html>";
    }
}

@RequestMapping("Inventory/condiments")
@Controller
class condimentsController
{
    @Autowired
    private  InventoryRepository repository;

    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Inventory/condiments";
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Inventory/condiments";
    }
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,@RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
      
    Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    Integer lowThresh = Integer.parseInt(low);
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    if(amt!=0)
    {
        inventoryItem.setOrderDate(date); 
    }
    
    inventoryItem.setName(name);
    inventoryItem.setLowThreshold(lowThresh);
    inventoryItem.setUnit(unit);


    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" changed");
    return "redirect:/Inventory/condiments";
        
    }


    @PostMapping("/add")
    String add( @RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
    Long id = this.repository.findMaxId();
    id+=1;
    System.out.println(id);
    LocalDate date = LocalDate.now();
    Inventory inventoryItem = new Inventory(id,name,"condiment",0,unit,Integer.parseInt(low),date);
     
    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" added");
    return "redirect:/Inventory/condiments";
        
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        try
        {
            System.out.println("going to delete Inventory  number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
        }
        catch(Exception e)
        {
            return handleException(id, e);
        }
    }

    public String handleException(Long id, Exception e) {
        // Log the exception details for debugging purposes
        System.err.println("An error occurred while processing request for ID: " + id);
        e.printStackTrace();

        // Return an HTML response indicating an error
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Error Message</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: rgba(0, 0, 0, 0.5); }" +
               ".popup { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 300px; padding: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); border-radius: 8px; background: #fff; z-index: 1000; }" +
               ".popup-content { text-align: center; }" +
               ".close-button { float: right; width: 1.5em; line-height: 1.5em; text-align: center; cursor: pointer; border-radius: 0.25em; background-color: lightgray; }" +
               ".close-button:hover { background-color: darkgray; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='popup'>" +
               "<div class='popup-content'>" +
               "<span class='close-button'>&times;</span>" +
               "<p>Error processing your request for Inventory ID " + id + ". ID not found or Item is used in a product, make sure no products use inventory item.</p>" +
               "</div>" +
               "</div>" +
               "<script>" +
               "document.querySelector('.close-button').addEventListener('click', function() {" +
               "document.querySelector('.popup').style.display = 'none';" +
               "});" +
               "</script>" +
               "</body>" +
               "</html>";
    }
}

@RequestMapping("Inventory/utilities")
@Controller
class utilitesController
{
    @Autowired
    private  InventoryRepository repository;

    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Inventory/utilities";
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Inventory/utilities";
    }
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,@RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
      
    Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    Integer lowThresh = Integer.parseInt(low);
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    if(amt!=0)
    {
        inventoryItem.setOrderDate(date); 
    }
    
    inventoryItem.setName(name);
    inventoryItem.setLowThreshold(lowThresh);
    inventoryItem.setUnit(unit);


    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" changed");
    return "redirect:/Inventory/utilities";
        
    }


    @PostMapping("/add")
    String add( @RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
    Long id = this.repository.findMaxId();
    id+=1;
    System.out.println(id);
    LocalDate date = LocalDate.now();
    Inventory inventoryItem = new Inventory(id,name,"utilities",0,unit,Integer.parseInt(low),date);
     
    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" added");
    return "redirect:/Inventory/utilities";
        
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        try
        {
            System.out.println("going to delete Inventory  number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
        }
        catch(Exception e)
        {
            return handleException(id, e);
        }
    }

    public String handleException(Long id, Exception e) {
        // Log the exception details for debugging purposes
        System.err.println("An error occurred while processing request for ID: " + id);
        e.printStackTrace();

        // Return an HTML response indicating an error
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Error Message</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: rgba(0, 0, 0, 0.5); }" +
               ".popup { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 300px; padding: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); border-radius: 8px; background: #fff; z-index: 1000; }" +
               ".popup-content { text-align: center; }" +
               ".close-button { float: right; width: 1.5em; line-height: 1.5em; text-align: center; cursor: pointer; border-radius: 0.25em; background-color: lightgray; }" +
               ".close-button:hover { background-color: darkgray; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='popup'>" +
               "<div class='popup-content'>" +
               "<span class='close-button'>&times;</span>" +
               "<p>Error processing your request for Inventory ID " + id + ". ID not found or Item is used in a product, make sure no products use inventory item.</p>" +
               "</div>" +
               "</div>" +
               "<script>" +
               "document.querySelector('.close-button').addEventListener('click', function() {" +
               "document.querySelector('.popup').style.display = 'none';" +
               "});" +
               "</script>" +
               "</body>" +
               "</html>";
    }
}

@RequestMapping("Inventory/toppings")
@Controller
class toppingsController
{
    @Autowired
    private  InventoryRepository repository;

    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Inventory/toppings";
    }
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Inventory/toppings";
    }
    @PostMapping
    String order( @RequestParam("new-quantity")String amount,@RequestParam("item-id")String id,@RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
      
    Long Id = Long.parseLong(id);
    Integer amt = Integer.parseInt(amount);
    LocalDate date = LocalDate.now();
    Integer lowThresh = Integer.parseInt(low);
    
    Inventory inventoryItem = this.repository.getById(Id);
     System.out.println(inventoryItem.toString());

    inventoryItem.setQuantity(inventoryItem.getQuantity() + amt);
    if(amt!=0)
    {
        inventoryItem.setOrderDate(date); 
    }
    
    inventoryItem.setName(name);
    inventoryItem.setLowThreshold(lowThresh);
    inventoryItem.setUnit(unit);


    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" changed");
    return "redirect:/Inventory/toppings";
        
    }


    @PostMapping("/add")
    String add( @RequestParam("item-name")String name,@RequestParam("item-unit")String unit,@RequestParam("item-low")String low,Model model)
    {
    Long id = this.repository.findMaxId();
    id+=1;
    System.out.println(id);
    LocalDate date = LocalDate.now();
    Inventory inventoryItem = new Inventory(id,name,"topping",0,unit,Integer.parseInt(low),date);
     
    this.repository.save(inventoryItem);
    model.addAttribute("inventory", this.repository.findAll());
    System.err.println(name +" added");
    return "redirect:/Inventory/toppings";
        
    }

    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        try
        {
            System.out.println("going to delete Inventory  number: "+id);
        repository.deleteById(id);// this is a querie from jpa repository
        return "";
        }
        catch(Exception e)
        {
            return handleException(id, e);
        }
    }

    public String handleException(Long id, Exception e) {
        // Log the exception details for debugging purposes
        System.err.println("An error occurred while processing request for ID: " + id);
        e.printStackTrace();

        // Return an HTML response indicating an error
        return "<!DOCTYPE html>" +
               "<html lang='en'>" +
               "<head>" +
               "<meta charset='UTF-8'>" +
               "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
               "<title>Error Message</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; height: 100vh; display: flex; justify-content: center; align-items: center; background-color: rgba(0, 0, 0, 0.5); }" +
               ".popup { position: fixed; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 300px; padding: 20px; box-shadow: 0 0 15px rgba(0, 0, 0, 0.2); border-radius: 8px; background: #fff; z-index: 1000; }" +
               ".popup-content { text-align: center; }" +
               ".close-button { float: right; width: 1.5em; line-height: 1.5em; text-align: center; cursor: pointer; border-radius: 0.25em; background-color: lightgray; }" +
               ".close-button:hover { background-color: darkgray; }" +
               "</style>" +
               "</head>" +
               "<body>" +
               "<div class='popup'>" +
               "<div class='popup-content'>" +
               "<span class='close-button'>&times;</span>" +
               "<p>Error processing your request for Inventory ID " + id + ". ID not found or Item is used in a product, make sure no products use inventory item.</p>" +
               "</div>" +
               "</div>" +
               "<script>" +
               "document.querySelector('.close-button').addEventListener('click', function() {" +
               "document.querySelector('.popup').style.display = 'none';" +
               "});" +
               "</script>" +
               "</body>" +
               "</html>";
    }
}


