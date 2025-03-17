package com.app.repository;
import org.springframework.data.jpa.repository.Query;
import com.app.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    
    // Get all tasks for a specific location
    List<Task> findByLocationId(Long locationId);
    
    // Get a task by its ID and location
    Optional<Task> findByIdAndLocationId(Long taskId, Long locationId);
    
    // Get tasks assigned to a specific employee in a location
    List<Task> findByLocationIdAndAssignedEmployeeId(Long locationId, Long employeeId);
    
    Optional<Task> findByIdAndLocationIdAndAssignedEmployeeId(Long taskId, Long locationId, Long employeeId);
    
    @Query("SELECT DISTINCT t.assignedEmployeeId FROM Task t " +
    	       "JOIN Employee e ON t.assignedEmployeeId = e.id " +
    	       "WHERE t.locationId = :locationId AND e.role = 'Cleaner'")
    	List<Long> findCleanerEmployeeIdsByLocation(Long locationId);


    @Query("SELECT e.id FROM Employee e " +
    	       "LEFT JOIN Task t ON e.id = t.assignedEmployeeId " +
    	       "WHERE e.role = 'Cleaner' " +
    	       "GROUP BY e.id " +
    	       "ORDER BY COUNT(t.id) ASC " +
    	       "LIMIT 1")
    	Long findCleanerWithLeastTasks();
    // Count the number of tasks assigned to an employee
    int countByAssignedEmployeeId(Long employeeId);
}
