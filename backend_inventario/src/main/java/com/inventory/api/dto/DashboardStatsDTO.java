package com.inventory.api.dto;

import java.util.List;

public class DashboardStatsDTO {
    private Integer totalProducts;
    private Double totalValue;
    private Integer lowStockItems;
    private Integer recentMovements;
    private List<CategoryStatsDTO> categories;

    public static class CategoryStatsDTO {
        private String name;
        private Integer count;

        public CategoryStatsDTO() {}

        public CategoryStatsDTO(String name, Integer count) {
            this.name = name;
            this.count = count;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }
    }

    // Constructors
    public DashboardStatsDTO() {}

    public DashboardStatsDTO(Integer totalProducts, Double totalValue, 
                           Integer lowStockItems, Integer recentMovements,
                           List<CategoryStatsDTO> categories) {
        this.totalProducts = totalProducts;
        this.totalValue = totalValue;
        this.lowStockItems = lowStockItems;
        this.recentMovements = recentMovements;
        this.categories = categories;
    }

    // Getters and Setters
    public Integer getTotalProducts() { return totalProducts; }
    public void setTotalProducts(Integer totalProducts) { this.totalProducts = totalProducts; }

    public Double getTotalValue() { return totalValue; }
    public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

    public Integer getLowStockItems() { return lowStockItems; }
    public void setLowStockItems(Integer lowStockItems) { this.lowStockItems = lowStockItems; }

    public Integer getRecentMovements() { return recentMovements; }
    public void setRecentMovements(Integer recentMovements) { this.recentMovements = recentMovements; }

    public List<CategoryStatsDTO> getCategories() { return categories; }
    public void setCategories(List<CategoryStatsDTO> categories) { this.categories = categories; }
}