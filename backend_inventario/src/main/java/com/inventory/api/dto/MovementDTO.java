package com.inventory.api.dto;

import com.inventory.api.model.InventoryMovement;
import jakarta.validation.constraints.*;

public class MovementDTO {
    @NotBlank(message = "El ID del producto es requerido")
    private String productId;
    
    @NotNull(message = "El tipo de movimiento es requerido")
    private InventoryMovement.MovementType type;
    
    @NotNull(message = "La cantidad es requerida")
    private Integer quantity;
    
    private String comments;
    
    @NotBlank(message = "El usuario que creó el movimiento es requerido")
    private String createdBy;

    // Constructors
    public MovementDTO() {}

    public MovementDTO(String productId, InventoryMovement.MovementType type, 
                      Integer quantity, String comments, String createdBy) {
        this.productId = productId;
        this.type = type;
        this.quantity = quantity;
        this.comments = comments;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public InventoryMovement.MovementType getType() { return type; }
    public void setType(InventoryMovement.MovementType type) { this.type = type; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}