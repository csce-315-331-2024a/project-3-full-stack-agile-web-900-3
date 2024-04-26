package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.Orders;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping(value = "/cashierPage")
@Controller // no logic in this this is purely mappping the index html to the javascript
class CashierController{
    private final ProductsRepository repository;
    private final OrdersRepository ordersRepository;
    CashierController(ProductsRepository repository,OrdersRepository ordersRepository){
        this.repository = repository;
        this.ordersRepository = ordersRepository;
    }
    @GetMapping
    String products(Model model)
    {
        model.addAttribute("products", this.repository.findAll());
        model.addAttribute("orders", this.ordersRepository.findAll());
        return "cashierPage";
    }
    @PostMapping(value = "/add")
    public String addOrder(@RequestParam("price") String price, Model model) {

        Long newId = ordersRepository.findMaxId()+1;
        LocalDate now = LocalDate.now();
        Date date = Date.valueOf(now);
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newId,newPrice,date,status);
        this.ordersRepository.save(newOrder);
        model.addAttribute("orders", this.ordersRepository.findAll());
        System.out.println("Order placed successfully.");
        return "redirect:/cashierPage";
    }

    
}
