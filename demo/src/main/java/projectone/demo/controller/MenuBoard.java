package projectone.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectone.demo.model.Products;
import projectone.demo.repository.ProductsRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(allProducts);
        return allProducts;
    }

    public List<String> getCategories(List<Products> prod_list){
        return prod_list.stream().map(item -> item.getProduct_type()).distinct().collect(Collectors.toList());
    }
    public HashMap<String, List<Products>> groupProducts(){
        // split up the productNames and prices by category
        // make a map that maps each unique category to a list of products
        Map<String, List<Products>> groupedProducts = productList.stream().collect(Collectors.groupingBy(Products::getProduct_type));
        //print each product for each category in the map
        for (Map.Entry<String, List<Products>> entry : groupedProducts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        return new HashMap<>(groupedProducts);

    }



//    public List<String> categories = productList.stream().map(item -> item.get(2)).distinct().collect(Collectors.toList());
//
//    //split up the productNames and prices by category
//    //make a map that maps each unique category to a list of products
//
//    record menuboardProducts(String productname, BigDecimal price){};
//    menuboardProducts[] product_obj_arr = new menuboardProducts[productList.size()];
//
//    HashMap<String, List<menuboardProducts>> prepData(){
//        HashMap<String, List<menuboardProducts>> categoryMap = new HashMap<>();
//        for(int i = 0; i < productList.size(); i++) {
//            product_obj_arr[i] = new menuboardProducts(productList.get(i).get(0), new BigDecimal(productList.get(i).get(1)));
//            if (categoryMap.containsKey(productList.get(i).get(2))) {
//                categoryMap.get(productList.get(i).get(2)).add(product_obj_arr[i]);
//            } else {
//                categoryMap.put(productList.get(i).get(2), new ArrayList<>(Arrays.asList(product_obj_arr[i])));
//            }
//        }
//        return categoryMap;
//    }
//    public HashMap<String, List<menuboardProducts>> categoryMap = prepData();







    @GetMapping("/menuboard")
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
        return "menuBoard";

    }

//    @GetMapping()
//    String test(Model model){
//        model.addAttribute("something", "this text");
//        return "index";
//    }




}
