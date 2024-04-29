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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import projectone.demo.model.Orders;
import projectone.demo.model.OrderProducts;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;
@RequestMapping("Manager/orders")
@Controller
class OrdersPageController {
    @Autowired
    private OrdersRepository orderRepository;
    private OrderProductsRepo orderProductsRepo;
    private ProductsRepository productsRepository;
    private LocalDateTime endDate;
    private LocalDateTime startDate;
    
    public OrdersPageController(OrdersRepository orderRepository, OrderProductsRepo orderProductsRepo,ProductsRepository productsRepository) {
        this.orderRepository = orderRepository;
        this.orderProductsRepo = orderProductsRepo;
        this.productsRepository = productsRepository;
    }

    @GetMapping
    String Orders(Model model,Model junctionModel,Model productModel)
    {
        
        LocalDateTime dateTime = LocalDateTime.now();
        startDate = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT);
        endDate = LocalDateTime.now();
        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, endDate);
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate, LocalDateTime.now());
        Orders firstOrder = datSorted.get(1);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);

        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        return "Manager/orders";
    }
    @PostMapping(value = "/add")
    String add(@RequestParam("price")String price,Model model)
    {
        Long newid = orderRepository.findMaxId()+1;
        LocalDateTime localDateTime = LocalDateTime.now(); 
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newid,newPrice,localDateTime,status);
        this.orderRepository.save(newOrder);
        model.addAttribute("orders", this.orderRepository.findAll());
        System.out.println("order added "+ newPrice);
        return "redirect:/Manager/orders";
    }
    @PostMapping(value = "/view")
    String view(@RequestParam("date")String date,Model model,Model junctionModel,Model productModel,RedirectAttributes redirectAttributes)
    {
        
        
        try{
            LocalDate localDate = LocalDate.parse(date);

       
        startDate = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);

        
        endDate = LocalDateTime.of(localDate, LocalTime.of(23, 59));
        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, endDate);
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate,endDate);
        Orders firstOrder = datSorted.get(1);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);
        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        }
        catch(Exception e)
        {
            redirectAttributes.addFlashAttribute("errorMessage", "Error: date has no orders");
        }
        return "Manager/orders";
    }
    @PostMapping(value = "/updateOrder")
     String update(@RequestParam("status")String newStatus, @RequestParam("Orderid")String id,@RequestParam("price")String price,@RequestParam("add")String add,@RequestParam("remove")String remove,Model junctionModel,Model model,Model productModel)
    {
        if(newStatus != "")
        {
        Orders newOrder = orderRepository.getById(Long.parseLong(id));
        newOrder.setPrice(new BigDecimal(price));
        newOrder.setStatus(newStatus);
        this.orderRepository.save(newOrder);
        }
        
        if(add != "")
        {
            Long newId = orderProductsRepo.findMaxId()+1;
            Long orderid = Long.parseLong(id);
            Long addnew = Long.parseLong(add);
            Long newQuantity = Long.parseLong("1");
            OrderProducts newJunction = new OrderProducts(newId,orderid,addnew,newQuantity);
        }
        if(remove != "")
        {

        }
        
      System.out.println("working");
      return("Manager/orders");
       
    }

 
    

}
