package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import com.app.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByLocationId(Long locationId);
    
    Optional<Employee> findByIdAndLocationId(Long id, Long locationId);
    
    Optional<Employee> findByEmailAndLocationId(String email, Long locationId);
    
    List<Employee> findByRoleAndLocationId(String role, Long locationId);
    
    List<Employee> findByWorkStatusAndLocationId(String status, Long locationId);
    
    List<Employee> findByShiftTimingAndLocationId(String shiftTiming, Long locationId);
    
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Employee e WHERE e.id = :employeeId AND e.role = 'Cleaner'")
    boolean isEmployeeCleaner(Long employeeId);
}
