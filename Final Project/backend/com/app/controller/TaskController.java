package com.app.controller;

import com.app.model.Task;
import com.app.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Get all tasks for a location
    @GetMapping("/all/{locationId}")
    public ResponseEntity<List<Task>> getAllTasks(@PathVariable Long locationId) {
        List<Task> tasks = taskService.getAllTasksByLocation(locationId);
        return ResponseEntity.ok(tasks);
    }

    // Get task by ID and location
    @GetMapping("/{locationId}/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long locationId, @PathVariable Long taskId) {
        Optional<Task> task = taskService.getTaskById(locationId, taskId);
        return task.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a task for a location
    @PostMapping("/create/{locationId}")
    public ResponseEntity<Task> createTask(@PathVariable Long locationId, @RequestBody Task task) {
        Task savedTask = taskService.createTask(locationId, task);
        return ResponseEntity.ok(savedTask);
    }

    // Update a task by ID and location
    @PutMapping("/{locationId}/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long locationId, @PathVariable Long taskId, @RequestBody Task updatedTask) {
        Task task = taskService.updateTask(locationId, taskId, updatedTask);
        return ResponseEntity.ok(task);
    } 

    // Delete a task by ID and location
    @DeleteMapping("/{locationId}/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long locationId, @PathVariable Long taskId) {
        taskService.deleteTask(locationId, taskId);
        return ResponseEntity.noContent().build();
    }

    // Mark task as completed by ID and location
    @PutMapping("/{locationId}/{taskId}/complete")
    public ResponseEntity<Task> completeTask(@PathVariable Long locationId, @PathVariable Long taskId) {
        Task task = taskService.markTaskAsCompleted(locationId, taskId);
        return ResponseEntity.ok(task); 
    } 
    
    @PutMapping("/accept/{locationId}/{employeeId}/{taskId}")
    public ResponseEntity<Task> acceptTask(
            @PathVariable Long locationId, 
            @PathVariable Long employeeId, 
            @PathVariable Long taskId) {
        
        Task updatedTask = taskService.acceptTask(locationId, employeeId, taskId);
        return ResponseEntity.ok(updatedTask);
    } 

    // Get tasks assigned to an employee for a location
    @GetMapping("/{locationId}/employee/{employeeId}")
    public ResponseEntity<List<Task>> getTasksByEmployee(@PathVariable Long locationId, @PathVariable Long employeeId) {
        List<Task> tasks = taskService.getTasksByEmployeeId(locationId, employeeId);
        return ResponseEntity.ok(tasks);
    }
    
    @PostMapping("/assign-cleaning-task/{locationId}")
    public ResponseEntity<Task> assignCleaningTask(@PathVariable Long locationId, @RequestBody Task task) {
        Task assignedTask = taskService.assignCleaningTask(locationId, task);
        return ResponseEntity.ok(assignedTask);
    }

    
}
