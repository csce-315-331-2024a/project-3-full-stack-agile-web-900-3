package projectone.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectone.demo.model.Orders;
import projectone.demo.repository.OrdersRepository;
@RequestMapping("/kitchen")
@Controller
 class kitchenController {
    @Autowired
    private OrdersRepository repository;

    
    String kitchenController(OrdersRepository repository)
        {
            this.repository = repository;
            return "kitchen";
        }

    @GetMapping
    String Orders(Model model)
    {
        model.addAttribute("pending", this.repository.findOrdersWithStatusPending());
        return "kitchen";
    }
    @PostMapping
    String update(@RequestParam("id")String id,@RequestParam("update")String update,Model model)
    {
        Orders newOrder = this.repository.getById(Long.parseLong(id));
        newOrder.setStatus("completed");
        this.repository.save(newOrder);
        model.addAttribute("pending",this.repository.findOrdersWithStatusPending());
        System.out.println("updated order "+id);
        return "redirect:/kitchen";
    }
}


