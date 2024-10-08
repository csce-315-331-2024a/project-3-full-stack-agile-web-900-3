package projectone.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import projectone.demo.model.ProductInventory;
import projectone.demo.repository.ProductInventoryRepository;

import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Chance Hughes
 */
/**
 * REST controller for accessing product inventory data.
 */
@RestController
@RequestMapping("/api")
public class ProductInventoryController {

    @Autowired
    private ProductInventoryRepository productInventoryRepository;
        /**
     * Retrieves a list of ingredient names for a specific product.
     *
     * @param productId the ID of the product
     * @return a list of ingredient names
     */
    // Fetch ingredients for a specific product
    @GetMapping("/products/{productId}/ingredients")
    public List<String> getProductIngredients(@PathVariable Long productId) {
        return productInventoryRepository.findIngredientNamesByProductId(productId);
    }
    
}