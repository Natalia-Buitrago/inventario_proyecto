package com.inventory.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Document(collection = "inventory_movements")
public class InventoryMovement {
    @Id
    private String id;
    
    @NotBlank(message = "El ID del producto es requerido")
    private String productId;
    
    @NotBlank(message = "El nombre del producto es requerido")
    private String productName;
    
    @NotBlank(message = "El código del producto es requerido")
    private String productCode;
    
    @NotNull(message = "El tipo de movimiento es requerido")
    private MovementType type;
    
    @NotNull(message = "La cantidad es requerida")
    private Integer quantity;
    
    @Min(value = 0, message = "El stock anterior no puede ser negativo")
    private Integer previousStock;
    
    @Min(value = 0, message = "El stock nuevo no puede ser negativo")
    private Integer newStock;
    
    private LocalDateTime date;
    private String comments;
    
    @NotBlank(message = "El usuario que creó el movimiento es requerido")
    private String createdBy;

    public enum MovementType {
        ENTRY("entry"),
        POSITIVE_ADJUSTMENT("positive_adjustment"),
        NEGATIVE_ADJUSTMENT("negative_adjustment"),
        RETURN("return");

        private final String value;

        MovementType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // Constructors
    public InventoryMovement() {
        this.date = LocalDateTime.now();
    }

    public InventoryMovement(String productId, String productName, String productCode,
                           MovementType type, Integer quantity, Integer previousStock,
                           Integer newStock, String comments, String createdBy) {
        this();
        this.productId = productId;
        this.productName = productName;
        this.productCode = productCode;
        this.type = type;
        this.quantity = quantity;
        this.previousStock = previousStock;
        this.newStock = newStock;
        this.comments = comments;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getPreviousStock() { return previousStock; }
    public void setPreviousStock(Integer previousStock) { this.previousStock = previousStock; }

    public Integer getNewStock() { return newStock; }
    public void setNewStock(Integer newStock) { this.newStock = newStock; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}