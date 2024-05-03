package projectone.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.OrderProducts;
import projectone.demo.model.Orders;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;


/**
 * Controller for kitchen operations related to order management.
 */
@RequestMapping("/kitchen")
@Controller
 public class kitchenController {
    @Autowired
    private OrdersRepository repository;
    private OrderProductsRepo orderprodRepository;
    private ProductsRepository productRepo;
    private 

        /**
     * Constructs a KitchenController with necessary repositories.
     *
     * @param repository       Repository for orders.
     * @param orderprodRepository  Repository for order products.
     * @param productRepo       Repository for products.
     */
    kitchenController(OrdersRepository repository,OrderProductsRepo orderprodRepository,ProductsRepository productRepo)
        {
            this.productRepo = productRepo;
            this.repository = repository;
            this.orderprodRepository = orderprodRepository;
           
        }
         /**
     * Displays the kitchen dashboard with orders, their details, and products.
     *
     * @param model     Model to pass orders to the view.
     * @param junction  Model to pass order products (junction data).
     * @param product   Model to pass products data.
     * @return          The kitchen view name.
     */
    @GetMapping
    String Orders(Model model,Model junction, Model product)
    {   

        try{
        List<Orders> listOfOrders = this.repository.findOrdersWithStatusProcessing();
        Orders firstOrder = listOfOrders.get(0);
        int size = listOfOrders.size();
        Orders lastOrder = listOfOrders.get(size-1);
        junction.addAttribute("junction", this.orderprodRepository.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders",listOfOrders );
        product.addAttribute("products", this.productRepo.findAll());
        return "kitchen";
    }
    catch(Exception e)
    {
        return "kitchen";
    }
    }
    /**
     * Updates the status of an order based on kitchen actions.
     *
     * @param id        ID of the order to update.
     * @param update    New status for the order.
     * @param model     Model to pass updated order data.
     * @return          Redirects back to the kitchen dashboard.
     */
    @PostMapping
    String update(@RequestParam("id")String id,@RequestParam("update")String update,Model model)
    {
        Orders newOrder = this.repository.getById(Long.parseLong(id));
        newOrder.setStatus(update);
        this.repository.save(newOrder);
        model.addAttribute("pending",this.repository.findOrdersWithStatusProcessing());
        System.out.println("updated order "+id);
        return "redirect:/kitchen";
    }
}


