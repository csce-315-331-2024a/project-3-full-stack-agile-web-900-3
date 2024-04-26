package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.Orders;
import projectone.demo.repository.OrdersRepository;
@RequestMapping("/orders")
@Controller
class OrdersPageController {
    @Autowired
    private OrdersRepository orderRepository;

    String OrdersPageController(OrdersRepository orderRepository)
        {
            this.orderRepository = orderRepository;
            return "orders";
        }

    @GetMapping
    String Orders(Model model)
    {
        model.addAttribute("orders", this.orderRepository.findAll());
        return "orders";
    }
    @PostMapping(value = "/add")
    String add(@RequestParam("price")String price,Model model)
    {
        Long newid = orderRepository.findMaxId()+1;
        LocalDate date = LocalDate.now();
        Date newdate = Date.valueOf(date);
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newid,newPrice,newdate,status);
        this.orderRepository.save(newOrder);
        model.addAttribute("orders", this.orderRepository.findAll());
        System.out.println("order added "+ newPrice);
        return "redirect:/orders";
    }

}
