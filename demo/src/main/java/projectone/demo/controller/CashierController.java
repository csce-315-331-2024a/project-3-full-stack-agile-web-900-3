package projectone.demo.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import projectone.demo.model.OrderProducts;
import projectone.demo.model.Orders;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductInventoryRepository;
import projectone.demo.repository.ProductsRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping(value = "/cashierPage")
@Controller // no logic in this this is purely mappping the index html to the javascript
class CashierController{
    private final ProductsRepository repository;
    private final OrdersRepository ordersRepository;
    private final OrderProductsRepo orderProductsRepo;
    private final ProductInventoryRepository productInventoryRepository;
    private final InventoryRepository inventoryRepository;

    CashierController(ProductsRepository repository,OrdersRepository ordersRepository, OrderProductsRepo orderProductsRepo, ProductInventoryRepository productInventoryRepository, InventoryRepository inventoryRepository){
        this.repository = repository;
        this.ordersRepository = ordersRepository;
        this.orderProductsRepo=orderProductsRepo;
        this.productInventoryRepository=productInventoryRepository;
        this.inventoryRepository=inventoryRepository;
    }
    @GetMapping
    String products(Model model, Model orderModel, Model orderProductsModel, Model productModel, Model productInventoryModel, Model inventoryModel)
    {
        List<String> prodTypes = Arrays.asList("sweet", "sandwich", "sauce", "burger",  "side", "beverage", "basket", "salad","seasonal");        
        productModel.addAttribute("productTypes", prodTypes);
        model.addAttribute("products", this.repository.getByProductType());
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        orderProductsModel.addAttribute("orderProducts", this.orderProductsRepo.getLastOrder());
        productInventoryModel.addAttribute("productInventory", this.productInventoryRepository.findAll());
        inventoryModel.addAttribute("inventory", this.inventoryRepository.findAll());
        return "cashierPage";
    }
    @PostMapping(value = "/add")
    public String add(@RequestParam("price") String price, @RequestParam("productId")String ids, @RequestParam("addId")String addIds, @RequestParam("noId")String noIds, Model orderModel, Model orderProductsModel) {

        String[] numbersArray = ids.split("-");
        
        // Convert the array of strings to an array of integers
        Long newId = ordersRepository.findMaxId()+1;
        int[] numbers = Arrays.stream(numbersArray).mapToInt(Integer::parseInt).toArray();
        
      
        LocalDateTime date = LocalDateTime.now();
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newId,newPrice,date,status);
        this.ordersRepository.save(newOrder);
        orderModel.addAttribute("orders", this.ordersRepository.getLastOrder());
        for(int i =0; i<numbers.length;i++)
        {
            Long productID = new Long(numbers[i]);
            Long newID = orderProductsRepo.findMaxId()+1;
            OrderProducts newJunction = new OrderProducts(newID,newId,productID,Long.parseLong("1"));
            this.orderProductsRepo.save(newJunction);
            orderProductsModel.addAttribute("orderProducts", this.orderProductsRepo.getLastOrder());

            List<Long> inventoryIds = productInventoryRepository.getInventoryIdsForProduct(productID);
            for (Long inventoryId : inventoryIds){
                inventoryRepository.updateInventoryQuantity(inventoryId, 1);
            }

        }
        if(addIds != ""){    
            String[] addIdsArray = addIds.split("-");
            int[] adds = Arrays.stream(addIdsArray).mapToInt(Integer::parseInt).toArray();
            for(int i=0;i<adds.length;i++){
                Long addId = new Long(adds[i]);
                inventoryRepository.updateInventoryQuantity(addId, 1);
            }
        }
        if(noIds != ""){
            String[] noIdsArray = noIds.split("-");
            int[] nos = Arrays.stream(noIdsArray).mapToInt(Integer::parseInt).toArray();
            for(int i=0;i<nos.length;i++){
                Long noId = new Long(nos[i]);
                inventoryRepository.updateInventoryQuantity(noId, -1);
            }
        }
        System.out.println("Order placed successfully.\n ordered: "+ids+" included: "+addIds+". not included: "+noIds);
        return "redirect:/cashierPage";
    }

    
}