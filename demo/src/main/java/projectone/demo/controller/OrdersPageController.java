package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.Orders;
import projectone.demo.model.OrderProducts;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;
@RequestMapping("/orders")
@Controller
class OrdersPageController {
    @Autowired
    private OrdersRepository orderRepository;
    private OrderProductsRepo orderProductsRepo;
    private ProductsRepository productsRepository;

    public OrdersPageController(OrdersRepository orderRepository, OrderProductsRepo orderProductsRepo,ProductsRepository productsRepository) {
        this.orderRepository = orderRepository;
        this.orderProductsRepo = orderProductsRepo;
        this.productsRepository = productsRepository;
    }

    @GetMapping
    String Orders(Model model,Model junctionModel,Model productModel)
    {
        
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT);
        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, LocalDateTime.now());
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate, LocalDateTime.now());
        Orders firstOrder = datSorted.get(1);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);
       
        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        return "orders";
    }
    @PostMapping(value = "/add")
    String add(@RequestParam("price")String price,Model model)
    {
        Long newid = orderRepository.findMaxId()+1;
        LocalDateTime localDateTime = LocalDateTime.now(); 
        Timestamp newdate = Timestamp.valueOf(localDateTime);
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newid,newPrice,newdate,status);
        this.orderRepository.save(newOrder);
        model.addAttribute("orders", this.orderRepository.findAll());
        System.out.println("order added "+ newPrice);
        return "redirect:/orders";
    }
    @PostMapping(value = "/view")
    String view(@RequestParam("date")String date,Model model,Model junctionModel,Model productModel)
    {
        
        
        LocalDate localDate = LocalDate.parse(date);

       
        LocalDateTime startDate = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);

        
        LocalDateTime endDate = LocalDateTime.of(localDate, LocalTime.of(23, 59));
        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, endDate);
        
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate,endDate);
        Orders firstOrder = datSorted.get(1);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);
    
        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        return "orders";
    }

}
