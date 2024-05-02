package projectone.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.sql.Timestamp;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import projectone.demo.repository.InventoryRepository;
import projectone.demo.projection.InventoryUsageStatistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InventoryService {

    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    public byte[] createInventoryChart(Timestamp startTime, Timestamp endTime, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Object[]> pageResult = inventoryRepository.findAggregatedInventoryUsage(startTime, endTime, pageRequest);

        List<InventoryUsageStatistic> usageStats = pageResult.getContent().stream()
            .map(obj -> new InventoryUsageStatistic(
                (String) obj[0],                     // itemName
                ((Number) obj[1]).longValue(),        // quantityUsed
                null)                                 // usageDate is not used here
            )
            .collect(Collectors.toList());

        logger.info("Number of usage stats on page: {}", usageStats.size());
        return generateChart(usageStats);
    }

    private byte[] generateChart(List<InventoryUsageStatistic> usageStats) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (InventoryUsageStatistic stat : usageStats) {
            dataset.addValue(stat.getQuantityUsed(), "Usage", stat.getItemName());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Inventory Usage",
                "Item",
                "Quantity Used",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ChartUtils.writeChartAsPNG(bos, barChart, 1000, 734);
            return bos.toByteArray();
        } catch (IOException e) {
            logger.error("Error in creating chart", e);
            throw new RuntimeException("Error in creating chart", e);
        }
    }
}