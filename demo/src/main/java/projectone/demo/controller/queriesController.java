package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;
import java.util.Base64;

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
import projectone.demo.projection.SalesTrendsProjection;
import projectone.demo.projection.OverstockProjection;
import projectone.demo.repository.SalesDataRepository;
import projectone.demo.repository.SalesTrendsRepository;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.service.InventoryService;  // Ensure this service is properly implemented to handle chart generation

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class queriesController {
    private final SalesDataRepository salesDataRepository;
    private final SalesTrendsRepository salesTrendsRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryService inventoryService;  // Dependency on the InventoryService
    private static final Logger logger = LoggerFactory.getLogger(queriesController.class);

    public queriesController(SalesDataRepository salesDataRepository, SalesTrendsRepository salesTrendsRepository, InventoryRepository inventoryRepository, InventoryService inventoryService) {
        this.salesDataRepository = salesDataRepository;
        this.salesTrendsRepository = salesTrendsRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryService = inventoryService; // Initializing the inventory service
    }

    @GetMapping("/queries")
    public String displayQueries(
        @RequestParam(value = "start_time", required = false, defaultValue = "2023-01-01T00:00") String startTime,
        @RequestParam(value = "end_time", required = false, defaultValue = "2023-12-31T23:59") String endTime,
        @RequestParam(value = "display", required = false, defaultValue = "data") String display,
        Model model) {

        startTime = startTime + ":00";
        endTime = endTime + ":00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.parse(startTime, formatter));
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.parse(endTime, formatter));

        switch (display) {
            case "trends":
                List<SalesTrendsProjection> salesTrends = salesTrendsRepository.findSalesTrends(startTimestamp, endTimestamp);
                model.addAttribute("trends", salesTrends);
                break;
            case "overstock":
                List<OverstockProjection> overstocks = inventoryRepository.findOverstock(startTimestamp, endTimestamp);
                model.addAttribute("overstocks", overstocks);
                break;
            case "inventoryChart":
                byte[] chart = inventoryService.createInventoryChart(startTimestamp, endTimestamp);  // Assuming this method generates and returns the chart as a byte array
                String base64Chart = Base64.getEncoder().encodeToString(chart);
                model.addAttribute("chartImage", base64Chart);
                logger.info("Chart Image (Base64) length: {}", base64Chart.length());
                break;
            default:
                List<SalesDataProjection> salesData = salesDataRepository.fetchSalesData(startTimestamp, endTimestamp);
                model.addAttribute("data", salesData);
        }

        model.addAttribute("display", display);
        return "queries";
    }
}
