package com.app.repository;

import com.app.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    
    Optional<UserModel> findByEmail(String email);  // Find user by email
    
    Optional<UserModel> findByUsername(String username); // Find user by username

    boolean existsByEmail(String email); // Check if email already exists

    boolean existsByAadharNumber(String aadharNumber); // Check if Aadhar number already exists
    
    @Query("SELECT u.userId FROM UserModel u WHERE u.email = :email")
    Optional<Long> findUserIdByEmail(@Param("email") String email);
}
