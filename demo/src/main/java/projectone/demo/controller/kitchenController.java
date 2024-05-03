package projectone.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import projectone.demo.model.Inventory;
import projectone.demo.model.OrderProducts;
import projectone.demo.model.Orders;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrderTicketRepository;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductInventoryRepository;
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
    private OrderTicketRepository orderAddonsRepo;
    private InventoryRepository invRepository;
    private ProductInventoryRepository prodInvrepo;

        /**
     * Constructs a KitchenController with necessary repositories.
     *
     * @param repository       Repository for orders.
     * @param orderprodRepository  Repository for order products.
     * @param productRepo       Repository for products.
     */
    kitchenController(OrdersRepository repository,OrderProductsRepo orderprodRepository,ProductsRepository productRepo,OrderTicketRepository orderAddonsRepo,InventoryRepository invRepository,ProductInventoryRepository prodInvrepo)
        {
            this.productRepo = productRepo;
            this.repository = repository;
            this.orderprodRepository = orderprodRepository;
            this.orderAddonsRepo = orderAddonsRepo;
            this.invRepository = invRepository;
            this.prodInvrepo = prodInvrepo;

           
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
    String Orders(Model model,Model junction, Model product,Model addons, Model inventory)
    {   

        try{
        List<Orders> listOfOrders = this.repository.findOrdersWithStatusProcessing();
        Orders firstOrder = listOfOrders.get(0);
        int size = listOfOrders.size();
        Orders lastOrder = listOfOrders.get(size-1);
        junction.addAttribute("junction", this.orderprodRepository.findOrderProductsByOrderIdBetween(firstOrder.getOrder_id(),lastOrder.getOrder_id()));
        model.addAttribute("orders",listOfOrders );
        product.addAttribute("products", this.productRepo.findAll());
        addons.addAttribute("addons", this.orderAddonsRepo.findAll());
        inventory.addAttribute("inventory", this.invRepository.findAll());
        
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
    String update(@RequestParam("id")String id,@RequestParam("update")String update,Model model, Model addons)
    {
        this.orderAddonsRepo.deleteById(Long.parseLong(id));
        Orders newOrder = this.repository.getById(Long.parseLong(id));
        newOrder.setStatus(update);
        System.out.println(update);
        if(update.equals("canceled"))
        {
            System.out.println("here");
            List restockProd = orderprodRepository.findProductIdsByOrderId(Long.parseLong(id));
            for(int i= 0 ;i<restockProd.size() ; i++)
            {
                
                List restockInv = prodInvrepo.getInventoryIdsForProduct(Long.parseLong(restockProd.get(i).toString()));
                for(int j = 0; j<restockInv.size(); j++)
                {
                    Inventory updateInv = this.invRepository.getById(Long.parseLong(restockInv.get(i).toString()));
                    this.invRepository.updateInventoryQuantity(Long.parseLong(restockInv.get(i).toString()), -1);
                }
            }
        }
        this.repository.save(newOrder);
        model.addAttribute("pending",this.repository.findOrdersWithStatusProcessing());
        addons.addAttribute("addons", this.orderAddonsRepo.findAll());
        System.out.println("updated order "+id);
        return "redirect:/kitchen";
    }
}


