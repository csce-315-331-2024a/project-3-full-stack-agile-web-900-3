package projectone.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import projectone.demo.model.Products;
import projectone.demo.repository.CustomerRepository;

import projectone.demo.repository.OrdersRepository;
import projectone.demo.model.Orders;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrdersRepository orderRepository;

    CustomerController(CustomerRepository customerRepository, OrdersRepository orderRepository)
    {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
        
    }

    @GetMapping
        public String Index() {
            return "customer"; 
    }

    @GetMapping("/orders")
    String Orders()
    {
        System.out.println("working");
        return "customer";
    }

    @PostMapping(value = "/add")
    String add(@RequestParam("price")String price)
    {
        System.out.println(price);
        Long newid = orderRepository.findMaxId()+1;
        LocalDateTime time = LocalDateTime.now();
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newid,newPrice,time,status);
        this.orderRepository.save(newOrder);
       
        System.out.println("order added");
        return "redirect:/customer/checkout";
    }

    @GetMapping("/api/menu/{category}")
    public ResponseEntity<List<Products>> getMenuByCategory(@PathVariable("category") String category) {
        List<Products> productsByCategory = customerRepository.findByProductType(category);
        if (productsByCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productsByCategory);
    }

    @GetMapping("/edit")
    public String editPage() {
        return "customerEditItem";
    }

    
    @GetMapping("/checkout")
    public String checkoutPage() {
        return "customerCheckout";
    }
}

