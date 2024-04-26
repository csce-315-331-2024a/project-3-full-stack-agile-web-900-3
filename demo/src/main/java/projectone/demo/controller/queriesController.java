package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import projectone.demo.model.SalesData;
import projectone.demo.model.SalesTrends;
import projectone.demo.projection.SalesDataProjection;
import projectone.demo.repository.SalesDataRepository;
import projectone.demo.repository.SalesTrendsRepository;

/*
@RequestMapping(value = "queries")
@Controller
class queriesController {
    private final SalesTrendsRepository salesTrendsRepo;
    private final SalesDataRepository salesDataRepo;

    queriesController(SalesTrendsRepository salesTrendsRepo, SalesDataRepository salesDataRepo)
    {
        this.salesDataRepo = salesDataRepo;
        this.salesTrendsRepo = salesTrendsRepo;
    }
    //populating model in code from database
    //@GetMapping
    // String queries(Model TrendModel, Model DataModel)
    // {
    //     TrendModel.addAttribute("trends", TrendModel);
    //     DataModel.addAttribute("data", DataModel);
    //     return "queries";
    // }
    @GetMapping
public String displayQueries(Model model) {
    if (true) {
        //model.addAttribute("trends", salesTrendsRepo.findSalesTrends());
       // model.addAttribute("data", salesDataRepo.fetchSalesData());
       List<Object[]> results = salesDataRepo.fetchSalesDataDebug();
    results.forEach(row -> System.out.println(Arrays.toString(row)));
       // System.out.println("Start Time: " + start_time + ", End Time: " + end_time);
    } 
    // else {
    //     System.out.println("No start time and/or end time provided.");
    // }
    return "queries";
}
  
}
*/

@Controller
public class queriesController {
    private final SalesDataRepository salesDataRepository;

    public queriesController(SalesDataRepository salesDataRepository) {
        this.salesDataRepository = salesDataRepository;
    }

    @GetMapping("/queries")
    public String displayQueries(
        @RequestParam(value = "start_time", required = false, defaultValue = "2023-01-01T00:00") String startTime,
        @RequestParam(value = "end_time", required = false, defaultValue = "2023-12-31T23:59") String endTime,
        Model model) {
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);
            LocalDateTime endDateTime = LocalDateTime.parse(endTime, formatter);

            List<SalesDataProjection> salesData = salesDataRepository.fetchSalesData(
                startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), 
                endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            model.addAttribute("data", salesData);
        } catch (DateTimeParseException e) {
            // Log the error or inform the user, and set default data or handle the exception
            model.addAttribute("error", "Invalid date format. Please use the format YYYY-MM-DDTHH:MM");
        }
        
        return "queries";
    }
}
