package projectone.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projectone.demo.model.Products;
import projectone.demo.model.WeatherResponse;
import projectone.demo.repository.ProductsRepository;

import java.awt.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Koby kuruvilla
 */
/**
 * controller for the menuboard.html page
 */

@RequestMapping(value = "/menuBoard")
@Controller
public class MenuBoard {
    private static final String URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu:5432/csce331_900_03_p3_db";
    private static final String USER = "csce331_900_03_user";
    private static final String PASSWORD = "snBjvgg8";
    private List<Products> productList;
    private List<String> categories;
    private HashMap<String, List<Products>> categoryMap;
    private final ProductsRepository productsRepository;
     /**
     * Constructs a {@code MenuBoardController} with the necessary product repository.
     *
     * @param productsRepository the repository for accessing product data
     */
    @Autowired
    public MenuBoard(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        productList = fetchDataFromDatabase();
        categories = getCategories(productList);
        categoryMap = groupProducts();
    }
   /**
     * Fetches product data from the database.
     *
     * @return a list of {@link Products}
     */
    public List<Products> fetchDataFromDatabase() {
        //
        List<Products> allProducts = productsRepository.findAll();

//        List<List<String>> productList = new ArrayList<>();
//
//        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
//            if (conn != null) {
//                String sql = "SELECT productname, price, product_type FROM products";
//
//                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
//                    System.out.println(rs);
//                    while (rs.next()) {
//                        List<String> item = new ArrayList<>();
//                        item.add(rs.getString("productname"));
//                        item.add(rs.getString("price"));
//                        item.add(rs.getString("product_type"));
//                        productList.add(item);
//                    }
//                }
//            }
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }

        return allProducts;
    }
     /**
     * Extracts unique categories from a list of products.
     *
     * @param prod_list the list of products
     * @return a list of distinct product categories
     */
    public List<String> getCategories(List<Products> prod_list){
        return prod_list.stream().map(item -> item.getProduct_type()).distinct().collect(Collectors.toList());
    }
     /**
     * Groups products by their categories.
     *
     * @return a hashmap linking product categories to lists of products
     */
    public HashMap<String, List<Products>> groupProducts(){
        // split up the productNames and prices by category
        // make a map that maps each unique category to a list of products
        Map<String, List<Products>> groupedProducts = productList.stream().collect(Collectors.groupingBy(Products::getProduct_type));
        return new HashMap<>(groupedProducts);

    }










    /**
     * Displays the menu board with product categories.
     *
     * @param model the model to carry data to the view
     * @return the menu board view name
     */
    @GetMapping
    String getPeople(Model model){
        productList = fetchDataFromDatabase();
        categories = getCategories(productList);
        categoryMap = groupProducts();

        double category_per_col = Math.round(categories.size() / 3.0);
        //create a list of list to store the indexes of the categoreis belonging to each column
        ArrayList<List<Integer>> categoryColumns = new ArrayList<>();

        int curr_index =0;
        for(int i =0; i<3; i++){
            categoryColumns.add(new ArrayList<>());
            for (int j = 0; j < category_per_col; j++) {
                categoryColumns.get(i).add(curr_index);
                curr_index++;
                if(curr_index >= categories.size()){
                    break;
                }
            }
        }


        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("categories", categories);
        model.addAttribute("category_per_col", category_per_col);
        model.addAttribute("categoryColumns", categoryColumns);
        model.addAttribute("weatherResponse", null);
        return "menuBoard";

    }

    record geoLocation(double latitude, double longitude){};
    public geoLocation weatherLocation;
    public WeatherResponse currentWeather_api;
    public String weatherIconLink;
    public String weatherCity;
    public String weatherDescription;
    public double weatherTemp;
       /**
     * Handles location data to fetch weather information.
     *
     * @param location the geographical location
     * @return redirect string to the menu board
     * @throws JsonProcessingException if JSON processing fails
     */
    @PostMapping
    public String handleLocation(@RequestBody geoLocation location) throws JsonProcessingException {
        productList = fetchDataFromDatabase();
        categories = getCategories(productList);
        categoryMap = groupProducts();

        double category_per_col = Math.round(categories.size() / 3.0);
        //create a list of list to store the indexes of the categoreis belonging to each column
        ArrayList<List<Integer>> categoryColumns = new ArrayList<>();

        int curr_index =0;
        for(int i =0; i<3; i++){
            categoryColumns.add(new ArrayList<>());
            for (int j = 0; j < category_per_col; j++) {
                categoryColumns.get(i).add(curr_index);
                curr_index++;
                if(curr_index >= categories.size()){
                    break;
                }
            }
        }




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
        double temp_faren = (int)(currentWeather_api.getMain().get("temp") - 273.15) * 9/5 + 32;
        currentWeather_api.getMain().put("temp", (float) temp_faren);
        weatherTemp = temp_faren;
        //create the source link for the weather icon
        weatherIconLink = "https://openweathermap.org/img/wn/" + currentWeather_api.getWeather()[0].getIcon() + ".png";
        //get the city name
        weatherCity = currentWeather_api.getName();
        //get the weather description
        weatherDescription = capitalizeEachWord(currentWeather_api.getWeather()[0].getDescription());

        System.out.println(currentWeather_api);
        weatherLocation = location;



        return "redirect:/menuBoard";
    }
     /**
     * Capitalizes each word in a string.
     *
     * @param input the string to capitalize
     * @return the capitalized string
     */
    // Function to capitialize the words in the Weather API
    public static String capitalizeEachWord(String input) {
        char[] chars = input.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'' || chars[i]=='-') { // Reset for next word
                found = false;
            }
        }
        return String.valueOf(chars);
    }



//    @PostMapping
//    public String postWeather(Model model, @RequestBody geoLocation location) throws JsonProcessingException {
//        model.addAttribute("shantanu", "adsfadsf");
//        weatherLocation = location;
//        System.out.println(location.latitude());
//        System.out.println(location.longitude());
//        return "redirect:/menuBoard";
//    }
//
//    @GetMapping
//    public String getTest(Model model) {
//        model.addAttribute("weather", weatherLocation);
//        System.out.println("get request made");
//        return "test";
//    }
//

    /**
     * Displays the weather fragment with current weather information.
     *
     * @param model the model to carry data to the view
     * @return the weather fragment view name
     */
    @GetMapping("/weather")
    public String getWeather(Model model) {
        model.addAttribute("weatherIconLink", weatherIconLink);
        model.addAttribute("weatherCity", weatherCity);
        model.addAttribute("weatherDescription", weatherDescription);
        model.addAttribute("weatherTemp", weatherTemp);
        return "fragments/weather";
    }






}
