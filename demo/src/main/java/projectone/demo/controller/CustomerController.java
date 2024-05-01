package projectone.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.client.RestTemplate;
import projectone.demo.model.Products;
import projectone.demo.model.WeatherResponse;
import projectone.demo.repository.CustomerRepository;

import projectone.demo.repository.OrdersRepository;
import projectone.demo.model.Orders;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.ProductsRepository;
import projectone.demo.model.OrderProducts;

import java.util.Arrays;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;
    private OrdersRepository orderRepository;
    private final OrderProductsRepo orderProductsRepo;

    CustomerController(CustomerRepository customerRepository, OrdersRepository orderRepository,
            OrderProductsRepo orderProductsRepo) {
        this.orderProductsRepo = orderProductsRepo;
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public String Index() {
        return "customer";
    }

    @GetMapping("/orders")
    String Orders() {
        System.out.println("working");
        return "customer";
    }

    @PostMapping(value = "/checkout")
    String add(@RequestParam("price") String price, @RequestParam("productId") String ids, Model orderModel,
            Model orderProductsModel) {

        String[] rawIdList = ids.split("-");

        Long newId = orderRepository.findMaxId() + 1;
        int[] idList = Arrays.stream(rawIdList).mapToInt(Integer::parseInt).toArray();

        LocalDateTime time = LocalDateTime.now();
        String status = "processing";
        BigDecimal newPrice = new BigDecimal(price);
        Orders newOrder = new Orders(newId, newPrice, time, status);
        this.orderRepository.save(newOrder);
        orderModel.addAttribute("orders", newOrder);

        for (long productID : idList) {
            Long newID = orderProductsRepo.findMaxId() + 1;
            OrderProducts newJunction = new OrderProducts(newID, newId, productID, 1L);
            this.orderProductsRepo.save(newJunction);
            orderProductsModel.addAttribute("orderProducts", newJunction);
        }

        System.out.println("order added");
        return "redirect:/customer"; // TODO: redirect to thank you page.
    }

    @GetMapping("/api/menu/{category}")
    public ResponseEntity<List<Products>> getMenuByCategory(@PathVariable("category") String category) {
        List<Products> productsByCategory = customerRepository.findByProductType(category);
        if (productsByCategory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productsByCategory);
    }

    @GetMapping("/edit")
    public String editPage() {
        return "customerEditItem";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "customerCheckout";
    }

    record geoLocation(double latitude, double longitude){};

    public geoLocation weatherLocation;
    public WeatherResponse currentWeather_api;
    public String weatherIconLink;
    public String weatherCity;
    public String weatherDescription;
    public double weatherTemp;

    @PostMapping("/location")
    public String handleLocation(@RequestBody geoLocation location) throws JsonProcessingException {

        // send an api request to openweather api to get the weather
        // the api format is https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
        // the api key is 85a9986100fc4b8928e3e17b2d74322c
        // the response will be in html format

        System.out.println(location.latitude());
        System.out.println(location.longitude());
        System.out.println("javascript recieved");

        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=85a9986100fc4b8928e3e17b2d74322c",
                location.latitude(), location.longitude());
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);

        //deserialize jsonResponse to WeatherResponse object
        ObjectMapper objectMapper = new ObjectMapper();
        currentWeather_api = objectMapper.readValue(jsonResponse, WeatherResponse.class);

        //convert the temperature from kelvin to farenheit and store
        double temp_faren = (currentWeather_api.getMain().get("temp") - 273.15) * 9/5 + 32;
        currentWeather_api.getMain().put("temp", (float) temp_faren);
        weatherTemp = temp_faren;
        //create the source link for the weather icon
        weatherIconLink = "https://openweathermap.org/img/wn/" + currentWeather_api.getWeather()[0].getIcon() + ".png";
        //get the city name
        weatherCity = currentWeather_api.getName();
        //get the weather description
        weatherDescription = currentWeather_api.getWeather()[0].getDescription();

        System.out.println(currentWeather_api);
        weatherLocation = location;



        return "redirect:/customer";
    }



}

