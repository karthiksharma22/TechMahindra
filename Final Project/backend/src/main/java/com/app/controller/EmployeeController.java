package com.app.controller;

import com.app.model.Employee;
import com.app.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    // Create a new employee for a specific location
    @PostMapping("/register/{locationId}")
    public ResponseEntity<Employee> registerEmployee(@PathVariable Long locationId, @RequestBody Employee employee) {
        employee.setLocationId(locationId); // Directly set locationId
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // Get all employees at a specific location
    @GetMapping("/all/{locationId}")
    public ResponseEntity<List<Employee>> getAllEmployees(@PathVariable Long locationId) {
        return ResponseEntity.ok(employeeService.getAllEmployeesByLocation(locationId));
    }

    // Get employee by ID and location
    @GetMapping("/{id}/location/{locationId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id, @PathVariable Long locationId) {
        Optional<Employee> employee = employeeService.getEmployeeByIdAndLocation(id, locationId);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get employee by email and location
    @GetMapping("/email/{email}/location/{locationId}")
    public ResponseEntity<Employee> getEmployeeByEmail(@PathVariable String email, @PathVariable Long locationId) {
        Optional<Employee> employee = employeeService.getEmployeeByEmailAndLocation(email, locationId);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get employees by role and location
    @GetMapping("/role/{role}/location/{locationId}")
    public ResponseEntity<List<Employee>> getEmployeesByRole(@PathVariable String role, @PathVariable Long locationId) {
        return ResponseEntity.ok(employeeService.getEmployeesByRoleAndLocation(role, locationId));
    }

    // Get employees by work status and location
    @GetMapping("/workstatus/{status}/location/{locationId}")
    public ResponseEntity<List<Employee>> getEmployeesByWorkStatus(@PathVariable String status, @PathVariable Long locationId) {
        return ResponseEntity.ok(employeeService.getEmployeesByWorkStatusAndLocation(status, locationId));
    }

    // Get employees by shift timing and location
    @GetMapping("/shift/{shiftTiming}/location/{locationId}")
    public ResponseEntity<List<Employee>> getEmployeesByShift(@PathVariable String shiftTiming, @PathVariable Long locationId) {
        return ResponseEntity.ok(employeeService.getEmployeesByShiftTimingAndLocation(shiftTiming, locationId));
    }

    // Update an employee at a specific location
    @PutMapping("/update/{id}/location/{locationId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @PathVariable Long locationId, @RequestBody Employee updatedEmployee) {
        Optional<Employee> employee = employeeService.updateEmployeeByLocation(id, locationId, updatedEmployee);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an employee at a specific location
    @DeleteMapping("/delete/{id}/location/{locationId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id, @PathVariable Long locationId) {
        boolean deleted = employeeService.deleteEmployeeByIdAndLocation(id, locationId);
        if (deleted) {
            return ResponseEntity.ok("Employee deleted successfully.");
        }
        return ResponseEntity.notFound().build();
    }
 
    // Employee login (Password Verification) with location filter
    @PostMapping("/login/location/{locationId}")
    public ResponseEntity<?> login(@PathVariable Long locationId, @RequestParam String email, @RequestParam String password) {
        Optional<Employee> employee = employeeService.getEmployeeByEmailAndLocation(email, locationId);
        if (employee.isPresent() && employeeService.verifyPassword(password, employee.get().getPassword())) {
        	return ResponseEntity.ok(employee.get());        }
        return ResponseEntity.status(401).body("Invalid email or password.");
    }
    
    @PutMapping("/clockin/{employeeId}/location/{locationId}")
    public ResponseEntity<String> clockIn(@PathVariable Long employeeId, @PathVariable Long locationId) {
        boolean updated = employeeService.clockInEmployee(employeeId, locationId);
        
        if (updated) {
            return ResponseEntity.ok("Employee clocked in successfully.");
        } else {
            return ResponseEntity.status(404).body("Employee not found or update failed.");
        }
    }
}
