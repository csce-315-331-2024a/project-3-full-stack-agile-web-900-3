package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.OrderProducts;
import projectone.demo.model.Orders;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping(value = "/cashierPage")
@Controller // no logic in this this is purely mappping the index html to the javascript
class CashierController{
    private final ProductsRepository repository;
    private final OrdersRepository ordersRepository;
    private final OrderProductsRepo orderProductsRepo;

    CashierController(ProductsRepository repository,OrdersRepository ordersRepository, OrderProductsRepo orderProductsRepo){
        this.repository = repository;
        this.ordersRepository = ordersRepository;
        this.orderProductsRepo=orderProductsRepo;
    }
    @GetMapping
    String products(Model model, Model orderModel, Model orderProductsModel)
    {
        model.addAttribute("products", this.repository.findAll());
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        orderProductsModel.addAttribute("orderProducts", this.orderProductsRepo.getLastOrder());
        return "cashierPage";
    }
    @PostMapping(value = "/add")
    public String add(@RequestParam("price") String price,@RequestParam("productId")String ids, Model orderModel,Model orderProductsModel) {

        String[] numbersArray = ids.split("-");
        
        // Convert the array of strings to an array of integers
        Long newId = ordersRepository.findMaxId()+1;
        int[] numbers = Arrays.stream(numbersArray).mapToInt(Integer::parseInt).toArray();
        
      
        LocalDateTime now = LocalDateTime.now();
        Timestamp date = Timestamp.valueOf(now);
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newId,newPrice,date,status);
        this.ordersRepository.save(newOrder);
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        for(int i =0; i<numbers.length;i++)
        {
            Long productID = new Long(numbers[i]);
            Long newID = orderProductsRepo.findMaxId()+1;
            OrderProducts newJunction = new OrderProducts(newID,newId,productID,1);
            this.orderProductsRepo.save(newJunction);
            orderProductsModel.addAttribute("orderProducts", this.orderProductsRepo.getLastOrder());
        }
        System.out.println("Order placed successfully.\n"+ids);
        return "redirect:/cashierPage";
    }

    
}
