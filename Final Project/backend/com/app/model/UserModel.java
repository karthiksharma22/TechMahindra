package com.app.model;
	

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Table name in the database


public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment ID
    private Long userId; 

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 12)
    private String aadharNumber;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private String gender;

    // Default Constructor
    public UserModel() {
    }

    // Parameterized Constructor
    public UserModel(Long userId, String username, String email, String password, String aadharNumber, int age, String gender) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.aadharNumber = aadharNumber;
        this.age = age;
        this.gender = gender;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // toString Method
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
