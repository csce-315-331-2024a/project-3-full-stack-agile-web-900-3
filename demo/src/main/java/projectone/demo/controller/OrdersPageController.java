package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
import projectone.demo.model.Products;
import projectone.demo.model.OrderProducts;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("Manager/orders")
@Controller
public class OrdersPageController {
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
    String Orders(Model model,Model junctionModel,Model productModel,Model deletModel)
    {
        try{
        LocalDateTime dateTime = LocalDateTime.now();
        startDate = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIDNIGHT);
        endDate = LocalDateTime.now();
        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, endDate);
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate, LocalDateTime.now());
        Orders firstOrder = datSorted.get(0);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);
        deletModel.addAttribute("deleteList", null);
        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        return "Manager/orders";
        }
        catch(Exception e)
        {
            return "Manager/orders";
        }
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
    String view(@RequestParam("date")String date,Model model,Model junctionModel,Model productModel)
    {
        
        System.out.println(date);
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
            System.out.println("catchBlock");
        }
       
        return "Manager/orders";
    }
    @PostMapping("/modifyPrice")
    String modify(@RequestParam("id")String id,@RequestParam("price")String price,Model model,Model junctionModel,Model productModel)
    {
        if(id!="")
        {
            System.out.println(id);
            String newid = id.replace(",", "").trim();
            Orders modifiedOrder = this.orderRepository.getById(Long.parseLong(newid));
            if(price !="")
            modifiedOrder.setPrice(new BigDecimal(price));
            this.orderRepository.save(modifiedOrder);
            System.out.println("modified price of:"+id);
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
        return "Manager/orders :: list";
    }
    @PostMapping("/modifyStatus")
    String modifyStatus(@RequestParam("id")String id,@RequestParam("status")String Status,Model model,Model junctionModel,Model productModel)
    {
        if(id!="")
        {
            System.out.println(id);
            String newid = id.replace(",", "").trim();
            Orders modifiedOrder = this.orderRepository.getById(Long.parseLong(newid));
            if(Status !="")
            modifiedOrder.setStatus(Status);
            this.orderRepository.save(modifiedOrder);
            System.out.println("modified status of:"+id);
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
        return "Manager/orders :: list";
    }
    @PostMapping("/addProduct")
    String AddItem(@RequestParam("id")String id,@RequestParam("prodId")String prodId,Model model,Model junctionModel,Model productModel)
    {
        if(id!="")
        {
            if(prodId != "")
            {
                Long newid = orderProductsRepo.findMaxId()+1;
                OrderProducts newJunction = new OrderProducts(newid, Long.parseLong(id), Long.parseLong(prodId), Long.parseLong("1"));
                orderProductsRepo.save(newJunction);
                Products addPrice = this.productsRepository.getById(Long.parseLong(prodId));
                Orders modifiedPrice = this.orderRepository.getById(Long.parseLong(id));
                BigDecimal newPrice = addPrice.getPrice().add( modifiedPrice.getPrice());
                // BigDecimal newPrice = BigDecimal(addPrice.getPrice());
                modifiedPrice.setPrice(newPrice);
                orderRepository.save(modifiedPrice);
                System.out.println("added "+prodId);
            }
           
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
        return "Manager/orders :: list";
    }
    @PostMapping("/removeProduct")
    String RemoveItem(@RequestParam("id")String id,@RequestParam("remove")String prodId,Model model,Model junctionModel,Model productModel)
    {
        if(id!="" && prodId!="")
        {
            
                
                List<OrderProducts> deleteList = orderProductsRepo.findByOrderIdAndProductId(Long.parseLong(id),Long.parseLong(prodId));
                Long idToDelete = deleteList.get(0).getOrder_product_id();
                System.out.println(idToDelete);
                this.orderProductsRepo.deleteById(idToDelete);

                Products addPrice = this.productsRepository.getById(Long.parseLong(prodId));
                Orders modifiedPrice = this.orderRepository.getById(Long.parseLong(id));
                BigDecimal newPrice = modifiedPrice.getPrice().subtract(addPrice.getPrice());
                // BigDecimal newPrice = BigDecimal(addPrice.getPrice());
                modifiedPrice.setPrice(newPrice);
                orderRepository.save(modifiedPrice);
                System.out.println("added "+prodId);
            
           
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
        return "Manager/orders :: list";
    }
    @PostMapping("/updateRemove")
    public String postMethodName(@RequestParam("id")String id,Model deletModel ) {
        List<Long> prodids  = orderProductsRepo.findProductIdsByOrderId(Long.parseLong(id));
        deletModel.addAttribute("deleteList", productsRepository.findAllById(prodids));

        return "Manager/orders :: remove";
    }
    @PostMapping("removeOrder")
    public String removeOrder(@RequestParam("id")String id,Model model,Model junctionModel,Model productModel ) {
        orderProductsRepo.deleteByOrderId(Long.parseLong(id));
        orderRepository.deleteById(Long.parseLong(id));
       


        ArrayList dat = this.orderRepository.findOrdersWithinDateRange(startDate, endDate);
        ArrayList<Orders> datSorted = this.orderRepository.findOrdersWithinDateRangeSorted(startDate,endDate);
        Orders firstOrder = datSorted.get(1);
        int size = datSorted.size();
        Orders lastOrder = datSorted.get(size-1);
        Collections.reverse(dat);
        junctionModel.addAttribute("junction", this.orderProductsRepo.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders", dat);
        productModel.addAttribute("products", productsRepository.findAll());
        System.out.println("deleting:" + id);
        return "Manager/orders :: list";
    }
    
    
    // @PostMapping(value = "/updateOrder")
    //  String update(@RequestParam("status")String newStatus, @RequestParam("Orderid")String id,@RequestParam("price")String price,@RequestParam("add")String add,@RequestParam("remove")String remove,Model junctionModel,Model model,Model productModel)
    // {
    //     if(newStatus != "")
    //     {
    //     Orders newOrder = orderRepository.getById(Long.parseLong(id));
    //     newOrder.setPrice(new BigDecimal(price));
    //     newOrder.setStatus(newStatus);
    //     this.orderRepository.save(newOrder);
    //     }
        
    //     if(add != "")
    //     {
    //         Long newId = orderProductsRepo.findMaxId()+1;
    //         Long orderid = Long.parseLong(id);
    //         Long addnew = Long.parseLong(add);
    //         Long newQuantity = Long.parseLong("1");
    //         OrderProducts newJunction = new OrderProducts(newId,orderid,addnew,newQuantity);
    //     }
    //     if(remove != "")
    //     {

    //     }
        
    //   System.out.println("working");
    //   return("Manager/orders");
       
    // }

 
    

}
