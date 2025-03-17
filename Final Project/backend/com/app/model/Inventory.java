package com.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int stockQuantity;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String category;

    @Column(name = "location_id", nullable = false)
    private Long locationId;
    
    @Column(nullable = false)
    private int minThreshold; 
    
    @Column(nullable = false)
    private String supplierEmail; 

    // Default constructor
    public Inventory() {}

    // Parameterized constructor
    public Inventory(String name, String description, int stockQuantity, double price, String category, Long locationId, int minThreshold, String supplierEmail) {
        this.name = name;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.price = price;
        this.category = category;
        this.locationId = locationId;
        this.minThreshold = minThreshold;
        this.supplierEmail = supplierEmail;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public int getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(int minThreshold) {
        this.minThreshold = minThreshold;
    }

    public String getSupplierEmail() {
        return supplierEmail;
    }

    public void setSupplierEmail(String supplierEmail) {
        this.supplierEmail = supplierEmail;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", locationId=" + locationId +
                ", minThreshold=" + minThreshold +
                ", supplierEmail='" + supplierEmail + '\'' +
                '}';
    }
}
