package com.inventory.api.service;

import com.inventory.api.model.Supplier;
import com.inventory.api.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(String id) {
        return supplierRepository.findById(id);
    }

    public Optional<Supplier> getSupplierByName(String name) {
        return supplierRepository.findByName(name);
    }

    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.existsByName(supplier.getName())) {
            throw new RuntimeException("Ya existe un proveedor con el nombre: " + supplier.getName());
        }
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(String id, Supplier supplier) {
        Supplier existingSupplier = supplierRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        if (!existingSupplier.getName().equals(supplier.getName()) && 
            supplierRepository.existsByName(supplier.getName())) {
            throw new RuntimeException("Ya existe un proveedor con el nombre: " + supplier.getName());
        }

        existingSupplier.setName(supplier.getName());
        existingSupplier.setContact(supplier.getContact());
        existingSupplier.setPhone(supplier.getPhone());
        existingSupplier.setEmail(supplier.getEmail());

        return supplierRepository.save(existingSupplier);
    }

    public void deleteSupplier(String id) {
        if (!supplierRepository.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }
        supplierRepository.deleteById(id);
    }
}