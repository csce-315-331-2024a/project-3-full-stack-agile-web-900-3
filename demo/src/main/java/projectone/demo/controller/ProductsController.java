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
/**
 * @author Quinn Bromley
 */
/**
 * Controller for managing products within a manager's role.
 */
@RequestMapping("manager")   
@Controller
public class ProductsController{
    private final ProductInventoryRepository repositoryJunction;
    private final ProductsRepository repository;
    private final InventoryRepository invRepository;
    // importing products repository
      /**
     * Constructs a ProductsController with necessary repositories.
     *
     * @param repository          Repository for products.
     * @param repositoryJunction  Repository for product inventory junction.
     * @param invRepository       Repository for inventory.
     */
    ProductsController(ProductsRepository repository,ProductInventoryRepository repositoryJunction,InventoryRepository invRepository){
        this.repository = repository;
        this.repositoryJunction = repositoryJunction;
        this.invRepository = invRepository;
    }
      /**
     * Displays all products, inventory junctions, and inventory items.
     *
     * @param model           Model to pass products to the view.
     * @param modelJunc       Model to pass inventory junctions.
     * @param modelinventory  Model to pass inventory items.
     * @return                View name for the manager page.
     */
    //populating model in code from database
    @GetMapping
    String products(Model model,Model modelJunc,Model modelinventory )
    {
       model.addAttribute("manager", this.repository.findAll());
       modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
       modelinventory.addAttribute("inventory", this.invRepository.findAll());
        return "manager";
    }
         /**
     * Deletes a product and its associated inventory junctions.
     *
     * @param id    ID of the product to delete.
     * @return      A response body indicator (empty string in this case).
     */
    @ResponseBody
    @DeleteMapping(value = "/{id}" ,produces = MediaType.TEXT_HTML_VALUE)
    String delete(@PathVariable Long id)
    {
        repositoryJunction.deleteAllByProductId(id);
        System.out.println("going to delete Product number: "+id);
        repository.deleteById(id);// this is a query from jpa repository

        return "";
    }
    /**
     * Adds a new product with specified details.
     *
     * @param name             Name of the new product.
     * @param price            Price of the new product.
     * @param type             Type of the new product.
     * @param model            Model to update products list.
     * @param modelJunc        Model to update junctions.
     * @param modelinventory   Model to update inventory items.
     * @return                 Thymeleaf fragment for the manager list.
     */
    @PostMapping("/add")
    String add(@RequestParam("new-productsName")String name,@RequestParam("new-productsPrice")String price,@RequestParam("new-productsType")String type,Model model,Model modelJunc, Model modelinventory)
    {
        Long newId = this.repository.findMaxId() +1;
        BigDecimal bdFromString = new BigDecimal(price);
        Products newProduct = new Products(newId, name, bdFromString, type);
        
        System.out.println("adding "+ name);
        this.repository.save(newProduct);
        model.addAttribute("manager", this.repository.findAll());
        modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
        modelinventory.addAttribute("inventory", this.invRepository.findAll());
        return "manager :: manager-list"; // in this one im sending back a Thymeleaf fragment
        
    }
       /**
     * Adds a new inventory junction for a product.
     *
     * @param invId        Inventory ID to associate with the product.
     * @param prodId       Product ID for the new junction.
     * @param modelJunc    Model to update junctions.
     * @return             Redirect to the manager page.
     */
    @PostMapping("/addInv")
    String addInv(@RequestParam("new-inv-id")String invId,@RequestParam("id")String prodId,Model modelJunc)
    {
        Long newId = this.repositoryJunction.findMaxId() +1;
        ProductInventory newJunc = new ProductInventory(newId, Long.parseLong(prodId), Long.parseLong(invId));
        System.out.println("adding new junction");
        this.repositoryJunction.save(newJunc);
        modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
        return "redirect:/manager"; // in this one im sending back a Thymeleaf fragment
        
    }
        /**
     * Removes an inventory junction.
     *
     * @param remove       ID of the junction to remove.
     * @param modelJunc    Model to update junctions.
     * @return             Redirect to the manager page.
     */
    @PostMapping("/removeInv")
    String removeInv(@RequestParam("remove")String remove,Model modelJunc)
    {
        System.out.println(remove);
        repositoryJunction.deleteById(Long.parseLong(remove));
        modelJunc.addAttribute("junction", this.repositoryJunction.findAll());
        return "redirect:/manager"; // in this one im sending back a Thymeleaf fragment
        
    }
     /**
     * Updates a product's details.
     *
     * @param name         New name for the product.
     * @param price        New price for the product.
     * @param type         New type for the product.
     * @param id           ID of the product to update.
     * @param model        Model to update products list.
     * @return             Redirect to the manager page.
     */
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