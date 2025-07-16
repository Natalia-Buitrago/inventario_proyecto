package com.inventory.api.service;

import com.inventory.api.model.Product;
import com.inventory.api.dto.ProductDTO;
import com.inventory.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Optional<Product> getProductByCode(String code) {
        return productRepository.findByCode(code);
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsBySupplier(String supplier) {
        return productRepository.findBySupplier(supplier);
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }

    public List<Product> searchProducts(String searchTerm) {
        return productRepository.findBySearchTerm(searchTerm);
    }

    public Product createProduct(ProductDTO productDTO) {
        if (productRepository.existsByCode(productDTO.getCode())) {
            throw new RuntimeException("Ya existe un producto con el código: " + productDTO.getCode());
        }

        Product product = new Product();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setSupplier(productDTO.getSupplier());
        product.setPurchasePrice(productDTO.getPurchasePrice());
        product.setSalePrice(productDTO.getSalePrice());
        product.setUnit(productDTO.getUnit());
        product.setMinimumStock(productDTO.getMinimumStock());
        product.setCurrentStock(0);

        return productRepository.save(product);
    }

    public Product updateProduct(String id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Check if code is being changed and if new code already exists
        if (!product.getCode().equals(productDTO.getCode()) && 
            productRepository.existsByCode(productDTO.getCode())) {
            throw new RuntimeException("Ya existe un producto con el código: " + productDTO.getCode());
        }

        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setCategory(productDTO.getCategory());
        product.setSupplier(productDTO.getSupplier());
        product.setPurchasePrice(productDTO.getPurchasePrice());
        product.setSalePrice(productDTO.getSalePrice());
        product.setUnit(productDTO.getUnit());
        product.setMinimumStock(productDTO.getMinimumStock());
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }

    public Product updateStock(String id, Integer newStock) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        if (newStock < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }

        product.setCurrentStock(newStock);
        product.setUpdatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public Double getTotalInventoryValue() {
        return productRepository.findAll().stream()
            .mapToDouble(product -> product.getCurrentStock() * product.getSalePrice())
            .sum();
    }
}