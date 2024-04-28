package projectone.demo.projection;

public interface OverstockProjection {
    Long getInventoryId();
    String getInventoryName();
    String getInventoryType();
    Integer getCurrentQuantity();
    String getUnit();
    Integer getThreshold();
    Long getUsageCount();
}