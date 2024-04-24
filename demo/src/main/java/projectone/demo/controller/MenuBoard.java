package projectone.demo.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectone.demo.model.Products;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MenuBoard {
    private static final String URL = "jdbc:postgresql://csce-315-db.engr.tamu.edu:5432/csce331_900_03_p3_db";
    private static final String USER = "csce331_900_03_user";
    private static final String PASSWORD = "snBjvgg8";

    public static List<List<String>> fetchDataFromDatabase() {
        List<List<String>> productList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                String sql = "SELECT productname, price, product_type FROM products";

                try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                    //System.out.println(rs);
                    while (rs.next()) {
                        List<String> item = new ArrayList<>();
                        item.add(rs.getString("productname"));
                        item.add(rs.getString("price"));
                        item.add(rs.getString("product_type"));
                        productList.add(item);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productList;
    }

    public List<List<String>> productList = fetchDataFromDatabase();

    public List<String> categories = productList.stream().map(item -> item.get(2)).distinct().collect(Collectors.toList());

    //split up the productNames and prices by category
    Products[] product_obj_arr = new Products[productList.size()];
    //make a map that maps each unique category to a list of products
    HashMap<String, List<Products>> prepData(){
        HashMap<String, List<Products>> categoryMap = new HashMap<>();
        for(int i = 0; i < productList.size(); i++) {
            product_obj_arr[i] = new Products(productList.get(i).get(0), new BigDecimal(productList.get(i).get(1)));
            if (categoryMap.containsKey(productList.get(i).get(2))) {
                categoryMap.get(productList.get(i).get(2)).add(product_obj_arr[i]);
            } else {
                categoryMap.put(productList.get(i).get(2), new ArrayList<>(Arrays.asList(product_obj_arr[i])));
            }
        }
        return categoryMap;
    }
    public HashMap<String, List<Products>> categoryMap = prepData();







    @GetMapping("/menuboard")
    String getPeople(Model model){

        //System.out.println(categoryMap);
        model.addAttribute("something", "hooray text");
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("categories", categories);
        return "menuBoard";
    }

//    @GetMapping()
//    String test(Model model){
//        model.addAttribute("something", "this text");
//        return "index";
//    }




}
