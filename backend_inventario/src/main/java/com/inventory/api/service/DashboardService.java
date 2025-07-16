package com.inventory.api.service;

import com.inventory.api.dto.DashboardStatsDTO;
import com.inventory.api.model.Product;
import com.inventory.api.repository.ProductRepository;
import com.inventory.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ProductService productService;

    @Autowired
    private InventoryMovementService movementService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public DashboardStatsDTO getDashboardStats() {
        List<Product> allProducts = productService.getAllProducts();
        
        Integer totalProducts = allProducts.size();
        Double totalValue = productService.getTotalInventoryValue();
        Integer lowStockItems = productService.getLowStockProducts().size();
        
        LocalDateTime weekAgo = LocalDateTime.now().minusDays(7);
        Integer recentMovements = movementService.getMovementCountSince(weekAgo);
        
        // Category statistics
        Map<String, Long> categoryCount = allProducts.stream()
            .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        
        List<DashboardStatsDTO.CategoryStatsDTO> categories = categoryCount.entrySet().stream()
            .map(entry -> new DashboardStatsDTO.CategoryStatsDTO(entry.getKey(), entry.getValue().intValue()))
            .collect(Collectors.toList());

        return new DashboardStatsDTO(totalProducts, totalValue, lowStockItems, recentMovements, categories);
    }
}