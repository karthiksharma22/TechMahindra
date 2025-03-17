package com.app.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;  

    @Column(nullable = false, length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TaskStatus status; // PENDING, IN_PROGRESS, COMPLETED

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dueDate;

    @Column(name = "assigned_employee_id") // Store only Employee ID
    private Long assignedEmployeeId;

    @Column(name = "location_id", nullable = false) // Added locationId
    private Long locationId;

    // Default Constructor
    public Task() {}

    // Parameterized Constructor
    public Task(String title, String description, TaskStatus status, Date dueDate, Long assignedEmployeeId, Long locationId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.assignedEmployeeId = assignedEmployeeId;
        this.locationId = locationId;
    }

    // Getters and Setters
    public Long getTaskId() {
        return id;
    }

    public void setTaskId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Long getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(Long assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }
}
