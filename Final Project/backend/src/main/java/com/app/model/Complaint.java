package com.app.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Complaint {

    public enum RegisteredBy {
        GUEST, EMPLOYEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String description;
    private String status;

    @Enumerated(EnumType.STRING)
    private RegisteredBy registeredBy;

    private Long locationId;
    private Long userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    public Complaint() {
    }

    public Complaint(String category, String description, String status, RegisteredBy registeredBy, Long locationId, Long userId) {
        this.category = category;
        this.description = description;
        this.status = status;
        this.registeredBy = registeredBy;
        this.locationId = locationId;
        this.userId = userId;
        this.createdAt = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }
 
    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisteredBy getRegisteredBy() {
        return registeredBy;
    }

    public void setRegisteredBy(RegisteredBy registeredBy) {
        this.registeredBy = registeredBy;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}