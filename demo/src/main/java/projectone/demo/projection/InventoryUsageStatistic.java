package projectone.demo.projection;

import java.sql.Timestamp;

public class InventoryUsageStatistic {
    private String itemName;
    private Long quantityUsed;
    private Timestamp usageDate; // This will hold the datetime including time part

    // Constructor matching fields to be filled directly from the query
    public InventoryUsageStatistic(String itemName, Long quantityUsed, Timestamp usageDate) {
        this.itemName = itemName;
        this.quantityUsed = quantityUsed;
        this.usageDate = usageDate;
    }

    // Getters
    public String getItemName() {
        return itemName;
    }

    public Long getQuantityUsed() {
        return quantityUsed;
    }

    public Timestamp getUsageDate() {
        return usageDate;
    }

    // Setters
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantityUsed(Long quantityUsed) {
        this.quantityUsed = quantityUsed;
    }

    public void setUsageDate(Timestamp usageDate) {
        this.usageDate = usageDate;
    }
}
