package projectone.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import projectone.demo.model.Products;
import projectone.demo.model.WeatherResponse;
import projectone.demo.repository.ProductsRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@WebMvcTest(MenuBoard.class)
public class seleniumMenuTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsRepository productsRepository;

    @Mock
    private RestTemplate restTemplate;

    private MenuBoard controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".html");

        controller = new MenuBoard(productsRepository);
        this.mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setViewResolvers(viewResolver)
            .build();
    }

    @Test
    public void testGetPeople() throws Exception {
        // Arrange - Prepare the mock repository to return a predefined list of products
        when(productsRepository.findAll()).thenReturn(Arrays.asList(
            new Products(1L, "Product1", BigDecimal.valueOf(10.00), "Category1")
        ));

        // Act & Assert - Perform the MVC request and check model attributes
        mockMvc.perform(get("/menuBoard"))
                .andExpect(status().isOk()) // Check if the response status is OK
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryMap"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categories"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("category_per_col"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("categoryColumns"))
                .andExpect(MockMvcResultMatchers.model().attribute("weatherResponse", Matchers.nullValue())); // Expect weatherResponse to be null
    }

    @Test
    public void testCapitalizeEachWord() {
        // Test basic input
        assertEquals("Hello World", MenuBoard.capitalizeEachWord("hello world"), "Capitalize simple sentence.");

        // Test input with multiple spaces
        assertEquals("Hello World", MenuBoard.capitalizeEachWord("   hello   world  "), "Capitalize with extra spaces.");

        // Test input with punctuation
        assertEquals("Hello World! This Is A Test.", MenuBoard.capitalizeEachWord("hello world! this is a test."), "Capitalize with punctuation.");

        // Test input that's already capitalized
        assertEquals("Hello World", MenuBoard.capitalizeEachWord("Hello World"), "Already capitalized.");

        // Test empty string
        assertEquals("", MenuBoard.capitalizeEachWord(""), "Empty string should return empty.");

        // Test null input
        assertEquals(null, MenuBoard.capitalizeEachWord(null), "Null input should return null.");
    }
}
