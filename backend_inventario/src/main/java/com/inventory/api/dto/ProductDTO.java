package com.inventory.api.dto;

import jakarta.validation.constraints.*;

public class ProductDTO {
    private String id;
    
    @NotBlank(message = "El código es requerido")
    private String code;
    
    @NotBlank(message = "El nombre es requerido")
    private String name;
    
    private String description;
    
    @NotBlank(message = "La categoría es requerida")
    private String category;
    
    @NotBlank(message = "El proveedor es requerido")
    private String supplier;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de compra debe ser mayor a 0")
    private Double purchasePrice;
    
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
    private Double salePrice;
    
    @NotBlank(message = "La unidad de medida es requerida")
    private String unit;
    
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer minimumStock;

    // Constructors
    public ProductDTO() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSupplier() { return supplier; }
    public void setSupplier(String supplier) { this.supplier = supplier; }

    public Double getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(Double purchasePrice) { this.purchasePrice = purchasePrice; }

    public Double getSalePrice() { return salePrice; }
    public void setSalePrice(Double salePrice) { this.salePrice = salePrice; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getMinimumStock() { return minimumStock; }
    public void setMinimumStock(Integer minimumStock) { this.minimumStock = minimumStock; }
}