package com.app.model;



import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 15)
    private String phone;

    @Column(nullable = false, length = 50)
    private String role;

    @Column(nullable = false, length = 50)
    private String department;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(nullable = false, length = 20)
    private String shiftTiming;  // Example: "Morning", "Night"

    @Column(nullable = false, length = 20)
    private String workStatus;  // Example: "Active", "On Leave"

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date dateOfJoining;

    @Column(nullable = false)
    private String password;  // Store hashed password
    
    @Column(name = "location_id", nullable = false)
    private Long locationId;

    // Default Constructor
    public Employee() {}

    // Parameterized Constructor
    public Employee(String name, String email, String phone, String role, String department, BigDecimal salary, 
                    String shiftTiming, String workStatus, Date dateOfJoining, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.department = department;
        this.salary = salary;
        this.shiftTiming = shiftTiming;
        this.workStatus = workStatus;
        this.dateOfJoining = dateOfJoining;
        this.password = password;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return id;
    }

    public void setEmployeeId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Date dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }


    // toString() method
    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", department='" + department + '\'' +
                ", salary=" + salary +
                ", shiftTiming='" + shiftTiming + '\'' +
                ", workStatus='" + workStatus + '\'' +
                ", dateOfJoining=" + dateOfJoining +
                '}';
    }
}

