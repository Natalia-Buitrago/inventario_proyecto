package com.inventory.api.repository;

import com.inventory.api.model.InventoryMovement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovementRepository extends MongoRepository<InventoryMovement, String> {
    
    List<InventoryMovement> findByProductId(String productId);
    
    List<InventoryMovement> findByProductIdOrderByDateDesc(String productId);
    
    List<InventoryMovement> findByType(InventoryMovement.MovementType type);
    
    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    List<InventoryMovement> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("{ 'date': { $gte: ?0 } }")
    List<InventoryMovement> findRecentMovements(LocalDateTime since);
    
    List<InventoryMovement> findTop10ByOrderByDateDesc();
}