package projectone.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import projectone.demo.model.Products;
import projectone.demo.repository.CustomerRepository;
import projectone.demo.repository.OrdersRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebMvcTest(CustomerController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private OrdersRepository ordersRepository;

    private List<Products> products;

    @BeforeEach
    void setUp() {
        Products burger1 = new Products(1L, "Revs Burger", new BigDecimal("5.59"), "burger");
        Products burger2 = new Products(2L, "Double Stack Cheeseburger", new BigDecimal("8.70"), "burger");
        Products burger3 = new Products(3L, "Classic Burger", new BigDecimal("5.49"), "burger");
        Products burger4 = new Products(4L, "Bacon Cheeseburger", new BigDecimal("6.99"), "burger");
        // ... add more as needed

        Products basket1 = new Products(5L, "Three Tender Basket", new BigDecimal("6.79"), "basket");
        Products basket2 = new Products(6L, "Four Steak Finger Basket", new BigDecimal("7.29"), "basket");

        // ... add more as needed

        Products sandwich1 = new Products(7L, "Gig Em Patty Melt", new BigDecimal("6.29"), "sandwich");
        Products sandwich2 = new Products(8L, "Howdy Spicy Ranch Chicken Strip Sandwich", new BigDecimal("6.99"), "sandwich");
        Products sandwich3 = new Products(9L, "Classic Crispy or Grilled Chicken Tender Sandwich", new BigDecimal("5.79"), "sandwich");
        Products sandwich4 = new Products(10L, "Grilled Cheese", new BigDecimal("3.49"), "sandwich");

        // ... add more as needed
        Products sweet1 = new Products(11L, "Chocolate Shake", new BigDecimal("3.99"), "sweet");
        Products sweet2 = new Products(12L, "Vanilla Shake", new BigDecimal("3.99"), "sweet");
        Products sweet3 = new Products(13L, "Strawberry Shake", new BigDecimal("3.99"), "sweet");
        Products sweet4 = new Products(14L, "Cappuccino Shake", new BigDecimal("3.99"), "sweet");
        Products sweet5 = new Products(15L, "Ice Cream", new BigDecimal("1.50"), "sweet");
        Products sweet6 = new Products(16L, "Chocolate Chip Cookie", new BigDecimal("1.30"), "sweet");
        Products sweet7 = new Products(17L, "Brownie", new BigDecimal("1.50"), "sweet");

        Products salad1 = new Products(18L, "Salad Bar", new BigDecimal("8.99"), "salad");

        Products beverage1 = new Products(19L, "Small Drink", new BigDecimal("2.25"), "beverage");
        Products beverage2 = new Products(20L, "Large Drink", new BigDecimal("2.45"), "beverage");
        Products beverage3 = new Products(21L, "Coffee", new BigDecimal("2.29"), "beverage");
        Products beverage4 = new Products(22L, "Cold Brew", new BigDecimal("3.65"), "beverage");

        Products side1 = new Products(23L, "Fries", new BigDecimal("1.50"), "side");
        Products side2 = new Products(24L, "Tater Tots", new BigDecimal("1.50"), "side");
        Products side3 = new Products(25L, "Onion Rings", new BigDecimal("1.50"), "side");
        Products side4 = new Products(26L, "Chips", new BigDecimal("1.50"), "side");

        Products sauce1 = new Products(27L, "Gig Em Sauce", new BigDecimal(".69"), "sauce");
        Products sauce2 = new Products(28L, "Buffalo Sauce", new BigDecimal(".69"), "sauce");
        Products sauce3 = new Products(29L, "BBQ Sauce", new BigDecimal(".69"), "sauce");
        Products sauce4 = new Products(30L, "Honey Mustard", new BigDecimal(".69"), "sauce");
        Products sauce5 = new Products(31L, "Spicy Ranch", new BigDecimal(".69"), "sauce");
        Products sauce6 = new Products(32L, "Ranch", new BigDecimal(".69"), "sauce");


        // Create lists by category as needed for your tests
        products = Arrays.asList(burger1, burger2, burger3, burger4,
        basket1, basket2,
        sandwich1, sandwich2, sandwich3, sandwich4,
        sweet1, sweet2, sweet3, sweet4, sweet5, sweet6, sweet7,
        salad1,
        beverage1, beverage2, beverage3, beverage4,
        side1, side2, side3, side4,
        sauce1, sauce2, sauce3, sauce4, sauce5, sauce6); 
    }

    @Test
    @WithMockUser
    void listMenuItems_ShouldReturnAllProducts() throws Exception {
        given(customerRepository.findAll()).willReturn(products);

        mockMvc.perform(get("/customer/api/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(32)))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    @WithMockUser
    void getMenuByCategory_ShouldReturnProductsOfCategory() throws Exception {
        given(customerRepository.findByProductType("burger")).willReturn(products.stream().filter(p -> p.getProductType().equals("burger")).collect(Collectors.toList()));

            mockMvc.perform(get("/customer/api/menu/{category}", "burger")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].product_type").value("burger"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void getMenuByCategory_ShouldReturnNotFoundForNonExistingCategory() throws Exception {
        given(customerRepository.findByProductType("non-existent")).willReturn(Arrays.asList());

        mockMvc.perform(get("/api/menu/{category}", "non-existent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}