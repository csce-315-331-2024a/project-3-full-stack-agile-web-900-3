package projectone.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import projectone.demo.model.Products;
import projectone.demo.repository.CustomerRepository;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/") // Base mapping for all methods in this controller
public class CustomerController {

    private final CustomerRepository customerRepository;

    @GetMapping("/customer")
    public ResponseEntity<List<Products>> listMenuItems() {
        List<Products> menuItems = customerRepository.findAll(); 
        return ResponseEntity.ok().body(menuItems);
    }
}

