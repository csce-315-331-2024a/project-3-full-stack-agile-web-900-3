package projectone.demo.model;

import java.util.Objects;

public class ItemWeekKey {
    private String itemName;
    private String week;

    public ItemWeekKey(String itemName, String week) {
        this.itemName = itemName;
        this.week = week;
    }

    public String getItemName() {
        return itemName;
    }

    public String getWeek() {
        return week;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemWeekKey that = (ItemWeekKey) o;
        return Objects.equals(itemName, that.itemName) && Objects.equals(week, that.week);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, week);
    }
}