package com.inventory.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Document(collection = "suppliers")
public class Supplier {
    @Id
    private String id;
    
    @NotBlank(message = "El nombre del proveedor es requerido")
    private String name;
    
    @NotBlank(message = "El contacto es requerido")
    private String contact;
    
    private String phone;
    
    @Email(message = "El email debe tener un formato válido")
    private String email;

    // Constructors
    public Supplier() {}

    public Supplier(String name, String contact, String phone, String email) {
        this.name = name;
        this.contact = contact;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}