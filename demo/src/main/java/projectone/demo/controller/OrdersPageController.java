package projectone.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import projectone.demo.repository.OrdersRepository;
@RequestMapping("/orders")
@Controller
class OrdersPageController {
    @Autowired
    private OrdersRepository repository;

    String OrdersPageController(OrdersRepository repository)
        {
            this.repository = repository;
            return "orders";
        }
    
}
