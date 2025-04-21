package com.app.service;

import com.app.model.Task;
import com.app.model.TaskStatus;
import com.app.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.repository.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Get all tasks for a location
    public List<Task> getAllTasksByLocation(Long locationId) {
        return taskRepository.findByLocationId(locationId);
    }

    // Get a task by ID and location
    public Optional<Task> getTaskById(Long locationId, Long taskId) {
        return taskRepository.findByIdAndLocationId(taskId, locationId);
    }

    // Create a new task for a location
    public Task createTask(Long locationId, Task task) {
        task.setLocationId(locationId); // Ensure task is assigned to a location
        return taskRepository.save(task);
    }

    // Update a task for a location
    public Task updateTask(Long locationId, Long taskId, Task updatedTask) {
        Optional<Task> existingTask = taskRepository.findByIdAndLocationId(taskId, locationId);
        if (existingTask.isPresent()) {
            Task task = existingTask.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setAssignedEmployeeId(updatedTask.getAssignedEmployeeId());
            task.setDueDate(updatedTask.getDueDate());
            return taskRepository.save(task);
        }
        throw new RuntimeException("Task not found for ID: " + taskId + " in location: " + locationId);
    }

    // Delete a task for a location
    public void deleteTask(Long locationId, Long taskId) {
        Optional<Task> task = taskRepository.findByIdAndLocationId(taskId, locationId);
        task.ifPresent(taskRepository::delete);
    }

    // Mark a task as completed
    public Task markTaskAsCompleted(Long locationId, Long taskId) {
        Optional<Task> task = taskRepository.findByIdAndLocationId(taskId, locationId);
        if (task.isPresent()) {
            Task completedTask = task.get();
            completedTask.setStatus(TaskStatus.COMPLETED);
            return taskRepository.save(completedTask);
        }
        throw new RuntimeException("Task not found for ID: " + taskId + " in location: " + locationId);
    }

    // Get tasks assigned to an employee for a specific location
    public List<Task> getTasksByEmployeeId(Long locationId, Long employeeId) {
        return taskRepository.findByLocationIdAndAssignedEmployeeId(locationId, employeeId);
    }
    
    public Task acceptTask(Long locationId, Long employeeId, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

        

        // Update status
        if (task.getStatus() == TaskStatus.PENDING) {
            task.setStatus(TaskStatus.IN_PROGRESS);
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task is not in PENDING state");
        }
    }
    
    public Task assignAutoTask(Long locationId, String role, Task task) {
        // Find the employee with the least tasks for the given role
        Long employeeId = taskRepository.findEmpWithLeastTasks(role);
        
        if (employeeId == null) {
            throw new RuntimeException("No available employees with role: " + role);
        }

        // Assign the task
        task.setAssignedEmployeeId(employeeId);
        task.setLocationId(locationId);
        task.setStatus(TaskStatus.PENDING); // Default status
        return taskRepository.save(task);
    }
}
