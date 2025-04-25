package com.app.empdept.service;

import com.app.empdept.model.Department;
import com.app.empdept.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}
