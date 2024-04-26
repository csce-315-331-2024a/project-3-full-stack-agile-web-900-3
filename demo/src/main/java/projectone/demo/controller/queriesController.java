package projectone.demo.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.sql.Timestamp;

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
import projectone.demo.repository.SalesDataRepository;
import projectone.demo.repository.SalesTrendsRepository;


@Controller
public class queriesController {
    private final SalesDataRepository salesDataRepository;
    private final SalesTrendsRepository salesTrendsRepository;

    public queriesController(SalesDataRepository salesDataRepository, SalesTrendsRepository salesTrendsRepository) {
        this.salesDataRepository = salesDataRepository;
        this.salesTrendsRepository = salesTrendsRepository;
    }

    @GetMapping("/queries")
    public String displayQueries(
        @RequestParam(value = "start_time", required = false, defaultValue = "2023-01-01T00:00") String startTime,
        @RequestParam(value = "end_time", required = false, defaultValue = "2023-12-31T23:59") String endTime,
        @RequestParam(value = "display", required = false, defaultValue = "data") String display,
        Model model) {

        // Append ":00" to startTime and endTime to include seconds
        startTime = startTime + ":00";
        endTime = endTime + ":00";

        // Parse the modified time strings to LocalDateTime, then convert to Timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.parse(startTime, formatter));
        Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.parse(endTime, formatter));

        if ("trends".equals(display)) {
            List<SalesTrendsProjection> salesTrends = salesTrendsRepository.findSalesTrends(startTimestamp, endTimestamp);
            model.addAttribute("trends", salesTrends);
            model.addAttribute("display", "trends");
        } else {
            List<SalesDataProjection> salesData = salesDataRepository.fetchSalesData(startTimestamp, endTimestamp);
            model.addAttribute("data", salesData);
            model.addAttribute("display", "data");
        }

        return "queries";
    }
}
