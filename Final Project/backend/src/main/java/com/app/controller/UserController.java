package com.app.controller;

import com.app.model.UserModel;
import com.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
 
    // Create a new user
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        UserModel savedUser = userService.saveUser(user);
        // Don't return the password in the response
        savedUser.setPassword(null);
        return ResponseEntity.ok(savedUser);
    }

    // Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        // Remove passwords from response
        users.forEach(user -> user.setPassword(null));
        return ResponseEntity.ok(users);
    }
 
    // Get user by ID
    @GetMapping("/{id}") 
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        Optional<UserModel> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        } 
    }
    
 // User login
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserModel loginRequest) {
        Optional<UserModel> userOpt = userService.getUserByEmail(loginRequest.getEmail());

        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            if (userService.verifyPassword(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }


    // Get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserModel> getUserByEmail(@PathVariable String email) {
        Optional<UserModel> userOpt = userService.getUserByEmail(email);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get user by username
    @GetMapping("/username/{username}")
    public ResponseEntity<UserModel> getUserByUsername(@PathVariable String username) {
        Optional<UserModel> userOpt = userService.getUserByUsername(username);
        if (userOpt.isPresent()) {
            UserModel user = userOpt.get();
            user.setPassword(null);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an existing user
    @PutMapping("/update/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel updatedUser) {
        Optional<UserModel> existingUserOpt = userService.getUserById(id);
        if (existingUserOpt.isPresent()) {
            UserModel existingUser = existingUserOpt.get();
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail());
            
            // Only update password if provided
            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                // Password will be encrypted in the service layer
                existingUser.setPassword(updatedUser.getPassword());
            }
            
            existingUser.setAadharNumber(updatedUser.getAadharNumber());
            existingUser.setAge(updatedUser.getAge());
            existingUser.setGender(updatedUser.getGender());
            
            UserModel savedUser = userService.saveUser(existingUser);
            // Don't return the password in the response
            savedUser.setPassword(null);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete user by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<UserModel> user = userService.getUserById(id);
        if (user.isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}