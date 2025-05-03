package com.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtTokenProvider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class TestUser {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserService userService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        userDTO = new UserDTO();
        userDTO.setUsername("Bhanu");
        userDTO.setEmail("bhanu@gmail.com");
        userDTO.setPassword("bhanu@123");
        userDTO.setRole("ADMIN");

        user = User.builder()
                .id(1L)
                .username("Bhanu")
                .email("bhanu@gmail.com")
                .password("encodedPassword")
                .role(User.Role.ADMIN)
                .build();
    }

    @Test
    public void testRegisterUser() {
        when(passwordEncoder.encode("bhanu@123")).thenReturn("encodedPassword");

        userService.registerUser(userDTO);

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLoginSuccess() {
        when(userRepository.findByUsername("Bhanu")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bhanu@123", "encodedPassword")).thenReturn(true);
        when(jwtTokenProvider.generateToken("Bhanu")).thenReturn("mocked-jwt-token");

        String token = userService.login(userDTO);

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
    }

    @Test
    public void testLoginUserNotFound() {
        when(userRepository.findByUsername("Bhanu")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.login(userDTO));
    }

    @Test
    public void testLoginInvalidPassword() {
        when(userRepository.findByUsername("Bhanu")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("bhanu@123", "encodedPassword")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.login(userDTO));
        assertEquals("Invalid credentials", ex.getMessage());
    }
}
