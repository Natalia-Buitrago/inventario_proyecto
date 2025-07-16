package com.inventory.api.repository;

import com.inventory.api.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    Optional<Product> findByCode(String code);
    
    List<Product> findByCategory(String category);
    
    List<Product> findBySupplier(String supplier);
    
    @Query("{ 'currentStock': { $lte: ?0 } }")
    List<Product> findByCurrentStockLessThanEqual(Integer stock);
    
    @Query("{ '$expr': { '$lte': ['$currentStock', '$minimumStock'] } }")
    List<Product> findLowStockProducts();
    
    @Query("{ '$or': [ " +
           "{ 'name': { $regex: ?0, $options: 'i' } }, " +
           "{ 'code': { $regex: ?0, $options: 'i' } }, " +
           "{ 'description': { $regex: ?0, $options: 'i' } } " +
           "] }")
    List<Product> findBySearchTerm(String searchTerm);
    
    boolean existsByCode(String code);
}