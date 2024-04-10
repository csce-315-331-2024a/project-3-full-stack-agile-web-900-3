package projectone.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import projectone.demo.repository.ManagerInventoryRepository;
import projectone.demo.repository.ManagerRepository;
import projectone.demo.model.Products;
import projectone.demo.model.Inventory;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/") // Base mapping for all methods in this controller
public class ManagerController {

    private final ManagerRepository managerRepository;
    private final ManagerInventoryRepository managerInventoryRepository;
    

    @GetMapping("/manager")
    public ResponseEntity<List<Products>> listMenuItems() {
        List<Products> menuItems = managerRepository.findAll(); 
        return ResponseEntity.ok().body(menuItems);
    }
   @GetMapping("/managerInventory")
    public ResponseEntity<List<Inventory>> listInventoryItems(){
        List<Inventory> inv = managerInventoryRepository.findAll();
        return ResponseEntity.ok().body(inv);
    }
}

