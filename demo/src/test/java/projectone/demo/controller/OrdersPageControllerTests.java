package projectone.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import projectone.demo.model.Orders;
import projectone.demo.model.Products;
import projectone.demo.model.OrderProducts;
import projectone.demo.repository.OrderProductsRepo;
import projectone.demo.repository.OrdersRepository;
import projectone.demo.repository.ProductsRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mockStatic;
import org.mockito.MockedStatic;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(OrdersPageController.class)
public class OrdersPageControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersRepository ordersRepository;

    @MockBean
    private OrderProductsRepo orderProductsRepo;

    @MockBean
    private ProductsRepository productsRepository;

    private Orders order;
    private Products product;

    @BeforeEach
    void setUp() {
        order = new Orders(1L, new BigDecimal("100.00"), LocalDateTime.now(), "processing");
        product = new Products(1L, "Sample Product", new BigDecimal("50.00"), "burger");

        ArrayList<Orders> orderList1 = new ArrayList<>();
        orderList1.add(order);
        orderList1.add(new Orders(2L, new BigDecimal("200.00"), LocalDateTime.now().minusDays(1), "completed"));

        given(ordersRepository.findOrdersWithinDateRangeSorted(any(LocalDateTime.class), any(LocalDateTime.class)))
            .willReturn(orderList1);
        given(ordersRepository.getReferenceById(any(Long.class))).willReturn(order);
        given(productsRepository.getReferenceById(any(Long.class))).willReturn(product);
        given(orderProductsRepo.findMaxId()).willReturn(0L);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddProductToOrderAndReturnUpdatedList() throws Exception {
        // Verify the list size before proceeding
        ArrayList<Orders> sortedOrders = ordersRepository.findOrdersWithinDateRangeSorted(LocalDateTime.now(), LocalDateTime.now());
        assert(sortedOrders.size() >= 2): "Insufficient orders in the list for testing";

        OrderProducts orderProduct = new OrderProducts(1L, 1L, 1L, 1L);
        given(orderProductsRepo.save(any(OrderProducts.class))).willReturn(orderProduct);

        mockMvc.perform(post("/Manager/orders/addProduct")
                .with(csrf())
                .param("id", "1")
                .param("prodId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("Manager/orders :: list"));

            verify(ordersRepository).save(argThat(savedOrder -> savedOrder.getOrder_id() == 1 && savedOrder.getPrice().equals(new BigDecimal("150.00"))));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldRemoveProductFromOrderAndReturnUpdatedList() throws Exception {
        // Setup - ensure there's an order product to remove
        OrderProducts orderProduct = new OrderProducts(1L, order.getOrder_id(), product.getProductID(), 1L);
        given(orderProductsRepo.findByOrderIdAndProductId(order.getOrder_id(), product.getProductID()))
            .willReturn(Arrays.asList(orderProduct));
        doNothing().when(orderProductsRepo).deleteById(orderProduct.getOrder_product_id());
        given(ordersRepository.getReferenceById(order.getOrder_id())).willReturn(order);

        // Act - perform the mock MVC call to remove the product
        mockMvc.perform(post("/Manager/orders/removeProduct")
                .with(csrf())
                .param("id", order.getOrder_id().toString())
                .param("remove", product.getProductID().toString()))
            .andExpect(status().isOk()) 
            .andExpect(view().name("Manager/orders :: list"));

        // Assert - verify interactions and state changes
        verify(orderProductsRepo).deleteById(orderProduct.getOrder_product_id());
        verify(ordersRepository).save(order); // Verify that order is saved after removal
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldModifyOrderStatusAndReturnUpdatedList() throws Exception {
        // Setup - prepare the data and mock dependencies
        String newStatus = "completed";
        given(ordersRepository.getReferenceById(any(Long.class))).willReturn(order);
        given(ordersRepository.save(any(Orders.class))).willReturn(order);

        // Act - perform the mock MVC call to modify the status
        mockMvc.perform(post("/Manager/orders/modifyStatus")
                .with(csrf())
                .param("id", order.getOrder_id().toString())
                .param("status", newStatus))
            .andExpect(status().isOk()) 
            .andExpect(view().name("Manager/orders :: list"));

        // Assert - verify interactions and state changes
        verify(ordersRepository).getReferenceById(order.getOrder_id());
        verify(ordersRepository).save(argThat(savedOrder -> savedOrder.getStatus().equals(newStatus.toString())));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldModifyOrderPriceAndReturnUpdatedList() throws Exception {
        // Setup - prepare the data and mock dependencies
        BigDecimal newPrice = new BigDecimal(100.00);
        given(ordersRepository.getReferenceById(any(Long.class))).willReturn(order);
        given(ordersRepository.save(any(Orders.class))).willReturn(order);

        // Act - perform the mock MVC call to modify the status
        mockMvc.perform(post("/Manager/orders/modifyPrice")
                .with(csrf())
                .param("id", order.getOrder_id().toString())
                .param("price", newPrice.toString()))
            .andExpect(status().isOk()) 
            .andExpect(view().name("Manager/orders :: list"));

        // Assert - verify interactions and state changes
        verify(ordersRepository).getReferenceById(order.getOrder_id());
        verify(ordersRepository).save(argThat(savedOrder -> savedOrder.getPrice().equals(newPrice)));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldViewOrdersForGivenDate() throws Exception {
        // Prepare test data
        String date = "2024-04-30";
        LocalDate localDate = LocalDate.parse(date);
        LocalDateTime startDate = LocalDateTime.of(localDate, LocalTime.MIDNIGHT);
        LocalDateTime endDate = LocalDateTime.of(localDate, LocalTime.of(23, 59));

        // Mock repository response
        ArrayList<Orders> orders = new ArrayList<>();
        orders.add(order);
        given(ordersRepository.findOrdersWithinDateRange(startDate, endDate)).willReturn(orders);
        given(ordersRepository.findOrdersWithinDateRangeSorted(startDate, endDate)).willReturn(orders);

        // Perform the mock MVC call
        mockMvc.perform(post("/Manager/orders/view")
                .with(csrf())
                .param("date", date))
            .andExpect(status().isOk())
            .andExpect(view().name("Manager/orders"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void shouldAddOrderAndRedirectToOrdersPage() throws Exception {
        // Prepare test data
        BigDecimal newPrice = new BigDecimal("150.00");
        String price = "150.00";

        // Perform the mock MVC call
        mockMvc.perform(post("/Manager/orders/add")
                .with(csrf())
                .param("price", price))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/Manager/orders"));

        // Verify repository save method is called with the correct order
        verify(ordersRepository).save(argThat(savedOrder ->
                savedOrder.getPrice().equals(newPrice) &&
                savedOrder.getStatus().equals("processing")));
    }

}