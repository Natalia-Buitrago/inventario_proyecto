package com.inventory.api.controller;

import com.inventory.api.model.InventoryMovement;
import com.inventory.api.dto.MovementDTO;
import com.inventory.api.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movements")
@CrossOrigin(origins = "*")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService movementService;

    @GetMapping
    public ResponseEntity<List<InventoryMovement>> getAllMovements() {
        List<InventoryMovement> movements = movementService.getAllMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovement> getMovementById(@PathVariable String id) {
        return movementService.getMovementById(id)
            .map(movement -> ResponseEntity.ok(movement))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryMovement>> getMovementsByProductId(@PathVariable String productId) {
        List<InventoryMovement> movements = movementService.getMovementsByProductId(productId);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<InventoryMovement>> getMovementsByType(@PathVariable InventoryMovement.MovementType type) {
        List<InventoryMovement> movements = movementService.getMovementsByType(type);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<InventoryMovement>> getMovementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<InventoryMovement> movements = movementService.getMovementsByDateRange(startDate, endDate);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<InventoryMovement>> getRecentMovements(@RequestParam(defaultValue = "7") int days) {
        List<InventoryMovement> movements = movementService.getRecentMovements(days);
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/latest")
    public ResponseEntity<List<InventoryMovement>> getLatestMovements() {
        List<InventoryMovement> movements = movementService.getLatestMovements();
        return ResponseEntity.ok(movements);
    }

    @PostMapping
    public ResponseEntity<InventoryMovement> createMovement(@Valid @RequestBody MovementDTO movementDTO) {
        try {
            InventoryMovement movement = movementService.createMovement(movementDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(movement);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}