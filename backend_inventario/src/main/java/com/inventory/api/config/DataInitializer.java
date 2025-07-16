package com.inventory.api.config;

import com.inventory.api.model.Category;
import com.inventory.api.model.Supplier;
import com.inventory.api.model.Product;
import com.inventory.api.repository.CategoryRepository;
import com.inventory.api.repository.SupplierRepository;
import com.inventory.api.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        // Initialize categories if they don't exist
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Electrónicos", "Productos electrónicos"));
            categoryRepository.save(new Category("Ropa", "Artículos de vestir"));
            categoryRepository.save(new Category("Hogar", "Productos para el hogar"));
            categoryRepository.save(new Category("Deportes", "Artículos deportivos"));
            categoryRepository.save(new Category("Libros", "Libros y material educativo"));
        }

        // Initialize suppliers if they don't exist
        if (supplierRepository.count() == 0) {
            supplierRepository.save(new Supplier("TechCorp", "Juan Pérez", "555-0101", "juan@techcorp.com"));
            supplierRepository.save(new Supplier("FashionPlus", "María García", "555-0102", "maria@fashionplus.com"));
            supplierRepository.save(new Supplier("HomeMart", "Carlos López", "555-0103", "carlos@homemart.com"));
            supplierRepository.save(new Supplier("SportZone", "Ana Martínez", "555-0104", "ana@sportzone.com"));
        }

        // Initialize sample products if they don't exist
        if (productRepository.count() == 0) {
            Product laptop = new Product("LAPTOP001", "Laptop HP Pavilion", "Laptop HP Pavilion 15 pulgadas",
                    "Electrónicos", "TechCorp", 800.0, 1200.0, "unidades", 5);
            laptop.setCurrentStock(15);
            productRepository.save(laptop);

            Product mouse = new Product("MOUSE001", "Mouse Inalámbrico", "Mouse inalámbrico ergonómico",
                    "Electrónicos", "TechCorp", 25.0, 45.0, "unidades", 10);
            mouse.setCurrentStock(3);
            productRepository.save(mouse);

            Product shirt = new Product("SHIRT001", "Camisa Casual", "Camisa casual de algodón",
                    "Ropa", "FashionPlus", 30.0, 60.0, "unidades", 15);
            shirt.setCurrentStock(25);
            productRepository.save(shirt);
        }
    }
}