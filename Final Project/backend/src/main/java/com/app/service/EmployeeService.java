package com.app.service;

import com.app.model.Employee;
import com.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Save a new employee for a specific location
    public Employee saveEmployee(Employee employee) {
        if (employee.getLocationId() == null) {
            throw new IllegalArgumentException("Location ID is required");
        }
        // Encrypt password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
        return employeeRepository.save(employee);
    }

    // Get all employees at a specific location
    public List<Employee> getAllEmployeesByLocation(Long locationId) {
        return employeeRepository.findByLocationId(locationId);
    }

    // Get an employee by ID and location
    public Optional<Employee> getEmployeeByIdAndLocation(Long id, Long locationId) {
        return employeeRepository.findByIdAndLocationId(id, locationId);
    }

    // Get an employee by email and location
    public Optional<Employee> getEmployeeByEmailAndLocation(String email, Long locationId) {
        return employeeRepository.findByEmailAndLocationId(email, locationId);
    }

    // Get employees by role and location
    public List<Employee> getEmployeesByRoleAndLocation(String role, Long locationId) {
        return employeeRepository.findByRoleAndLocationId(role, locationId);
    }

    // Get employees by work status and location
    public List<Employee> getEmployeesByWorkStatusAndLocation(String status, Long locationId) {
        return employeeRepository.findByWorkStatusAndLocationId(status, locationId);
    }

    // Get employees by shift timing and location
    public List<Employee> getEmployeesByShiftTimingAndLocation(String shiftTiming, Long locationId) {
        return employeeRepository.findByShiftTimingAndLocationId(shiftTiming, locationId);
    }

    // Update an employee at a specific location
    public Optional<Employee> updateEmployeeByLocation(Long id, Long locationId, Employee updatedEmployee) {
        Optional<Employee> existingEmployeeOpt = employeeRepository.findByIdAndLocationId(id, locationId);

        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();

            // Update only non-null fields
            if (updatedEmployee.getName() != null) existingEmployee.setName(updatedEmployee.getName());
            if (updatedEmployee.getEmail() != null) existingEmployee.setEmail(updatedEmployee.getEmail());
            if (updatedEmployee.getPhone() != null) existingEmployee.setPhone(updatedEmployee.getPhone());
            if (updatedEmployee.getRole() != null) existingEmployee.setRole(updatedEmployee.getRole());
            if (updatedEmployee.getDepartment() != null) existingEmployee.setDepartment(updatedEmployee.getDepartment());
            if (updatedEmployee.getSalary() != null) existingEmployee.setSalary(updatedEmployee.getSalary());
            if (updatedEmployee.getShiftTiming() != null) existingEmployee.setShiftTiming(updatedEmployee.getShiftTiming());
            if (updatedEmployee.getWorkStatus() != null) existingEmployee.setWorkStatus(updatedEmployee.getWorkStatus());
            if (updatedEmployee.getDateOfJoining() != null) existingEmployee.setDateOfJoining(updatedEmployee.getDateOfJoining());

            // Prevent locationId from being changed
            existingEmployee.setLocationId(locationId);

            // Update password only if a new one is provided
            if (updatedEmployee.getPassword() != null && !updatedEmployee.getPassword().isEmpty()) {
                existingEmployee.setPassword(passwordEncoder.encode(updatedEmployee.getPassword()));
            }

            return Optional.of(employeeRepository.save(existingEmployee));
        }
        return Optional.empty();
    }

    // Delete an employee by ID and location
    public boolean deleteEmployeeByIdAndLocation(Long id, Long locationId) {
        Optional<Employee> employee = employeeRepository.findByIdAndLocationId(id, locationId);
        if (employee.isPresent()) {
            employeeRepository.delete(employee.get());
            return true;
        }
        return false;
    }

    // Verify password during login
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    public boolean clockInEmployee(Long employeeId, Long locationId) {
        Optional<Employee> employeeOpt = employeeRepository.findByIdAndLocationId(employeeId, locationId);
        
        if (employeeOpt.isPresent()) {
            Employee employee = employeeOpt.get();
            employee.setWorkStatus("Active");  // Update status to AVAILABLE
            employeeRepository.save(employee);  // Save updated status
            return true;
        }
        
        return false;  // Employee not found
    }
}
