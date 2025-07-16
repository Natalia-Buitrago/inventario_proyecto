package com.inventory.api.service;

import com.inventory.api.model.InventoryMovement;
import com.inventory.api.model.Product;
import com.inventory.api.dto.MovementDTO;
import com.inventory.api.repository.InventoryMovementRepository;
import com.inventory.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryMovementService {

    @Autowired
    private InventoryMovementRepository movementRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<InventoryMovement> getAllMovements() {
        return movementRepository.findAll();
    }

    public Optional<InventoryMovement> getMovementById(String id) {
        return movementRepository.findById(id);
    }

    public List<InventoryMovement> getMovementsByProductId(String productId) {
        return movementRepository.findByProductIdOrderByDateDesc(productId);
    }

    public List<InventoryMovement> getMovementsByType(InventoryMovement.MovementType type) {
        return movementRepository.findByType(type);
    }

    public List<InventoryMovement> getMovementsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return movementRepository.findByDateBetween(startDate, endDate);
    }

    public List<InventoryMovement> getRecentMovements(int days) {
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return movementRepository.findRecentMovements(since);
    }

    public List<InventoryMovement> getLatestMovements() {
        return movementRepository.findTop10ByOrderByDateDesc();
    }

    @Transactional
    public InventoryMovement createMovement(MovementDTO movementDTO) {
        Product product = productRepository.findById(movementDTO.getProductId())
            .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + movementDTO.getProductId()));

        Integer previousStock = product.getCurrentStock();
        Integer quantity = movementDTO.getQuantity();

        // Adjust quantity based on movement type
        Integer adjustedQuantity = quantity;
        if (movementDTO.getType() == InventoryMovement.MovementType.NEGATIVE_ADJUSTMENT) {
            adjustedQuantity = -Math.abs(quantity);
        }

        Integer newStock = previousStock + adjustedQuantity;

        // Validate that stock doesn't go negative
        if (newStock < 0) {
            throw new RuntimeException("No se puede reducir el stock por debajo de 0. Stock actual: " + previousStock);
        }

        // Create movement record
        InventoryMovement movement = new InventoryMovement();
        movement.setProductId(product.getId());
        movement.setProductName(product.getName());
        movement.setProductCode(product.getCode());
        movement.setType(movementDTO.getType());
        movement.setQuantity(adjustedQuantity);
        movement.setPreviousStock(previousStock);
        movement.setNewStock(newStock);
        movement.setComments(movementDTO.getComments());
        movement.setCreatedBy(movementDTO.getCreatedBy());

        // Update product stock
        product.setCurrentStock(newStock);
        product.setUpdatedAt(LocalDateTime.now());
        productRepository.save(product);

        // Save movement
        return movementRepository.save(movement);
    }

    public Integer getMovementCountSince(LocalDateTime since) {
        return movementRepository.findRecentMovements(since).size();
    }
}