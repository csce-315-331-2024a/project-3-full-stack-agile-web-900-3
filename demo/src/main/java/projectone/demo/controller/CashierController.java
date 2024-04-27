package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    String products(Model model, Model orderModel)
    {
        model.addAttribute("products", this.repository.findAll());
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        return "cashierPage";
    }
    @PostMapping(value = "/add")
    public String add(@RequestParam("price") String price, Model orderModel) {

        Long newId = ordersRepository.findMaxId()+1;
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newId,newPrice,date,status);
        this.ordersRepository.save(newOrder);
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        System.out.println("Order placed successfully.");
        return "redirect:/cashierPage";
    }

    
}
