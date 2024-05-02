package projectone.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import projectone.demo.model.Inventory;
import projectone.demo.repository.InventoryRepository;

/**
 * this is the controller for the desert.html page
 */
@RequestMapping("Manager/Inventory/desert")
@Controller
class desertController
{
    @Autowired
    private  InventoryRepository repository;
    /**
     *  This is the constructor for the desert  controller it takes in a repositoy from the sql database specifically the inventory table
     * @param repository the inventory table repository ie the values in the inventory table, and queries in the repository file
     */
    String foodController(InventoryRepository repository)
    {
        this.repository = repository;
        return "Manager/Inventory/desert";
    }
     /**
     * 
     * @param model sets the model for the file
     * @return the path to the html file
     */
    @GetMapping
    String Inventory(Model model)
    {
        model.addAttribute("inventory", this.repository.findAll());

        return "Manager/Inventory/desert";
    }
       /**
     * this is the method that modifies an entry in the inventory including ordering more stock
     * @param amount amount the user wants to order to add to the inventory
     * @param id this is the id of the inventory item
     * @param name this is the new name the manager enters
     * @param unit this is the new unit type the manager enters 
     * @param low this is the new low threshold the manager wants to enter
     * @param model this is the model that is being modified by all these changes, this is where the html connects from the database so updating this is updating what the html recives.
     * @return this method returns a redirect to the html page to update the data changed
     */
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
    return "redirect:/Manager/Inventory/desert";
        
    }

     /**
     * this is the logic behind adding a new item to the inventory
     * @param name this is the name of the new inventory item   
     * @param unit this is the unit of the new inventory item
     * @param low this is the low threshold of the new inventory item
     * @param model this is the model that is being modified by all these changes, this is where the html connects from the database so updating this is updating what the html recives.
     * @return this returns a path to the html file
     */
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
    return "redirect:/Manager/Inventory/desert";
        
    }
    /**
     * this is the method that deletes an inventory item from the database
     * @param id this is the id of the inventory item that needs to be delted
     * @return this returns nothing because we do not want to return any html, we just want to delete an item, if the item is in a product you cannot delete it, so i return html that tells the user why it cannot be deleted
     */
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
    
    
    /**
 * 
 * @param id the id value of the inventory item
 * @param e the exception that is caught
 * @return  if the item is in a product you cannot delete it, so i return html that tells the user why it cannot be deleted
 */
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

