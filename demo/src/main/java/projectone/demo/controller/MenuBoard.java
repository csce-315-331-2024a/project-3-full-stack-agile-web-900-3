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

    @Autowired
    public MenuBoard(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        productList = fetchDataFromDatabase();
        categories = getCategories(productList);
        categoryMap = groupProducts();
    }
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

    public List<String> getCategories(List<Products> prod_list){
        return prod_list.stream().map(item -> item.getProduct_type()).distinct().collect(Collectors.toList());
    }
    public HashMap<String, List<Products>> groupProducts(){
        // split up the productNames and prices by category
        // make a map that maps each unique category to a list of products
        Map<String, List<Products>> groupedProducts = productList.stream().collect(Collectors.groupingBy(Products::getProduct_type));
        return new HashMap<>(groupedProducts);

    }











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

    public static String capitalizeEachWord(String input) {
        if (input == null) return null;  // Handle null input gracefully.
    
        StringBuilder result = new StringBuilder(input.length());
        String[] words = input.trim().split("\\s+"); // Split input into words, removing extra spaces.
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0))); // Capitalize the first letter.
                result.append(word.substring(1).toLowerCase()); // Convert the rest of the word to lowercase.
                result.append(" "); // Append a space after each word.
            }
        }
        return result.toString().trim(); // Return the trimmed result to remove the last space.
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
    @GetMapping("/weather")
    public String getWeather(Model model) {
        model.addAttribute("weatherIconLink", weatherIconLink);
        model.addAttribute("weatherCity", weatherCity);
        model.addAttribute("weatherDescription", weatherDescription);
        model.addAttribute("weatherTemp", weatherTemp);
        return "fragments/weather";
    }






}
