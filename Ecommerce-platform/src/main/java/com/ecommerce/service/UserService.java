package com.ecommerce.service;

import com.ecommerce.dto.UserDTO;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.model.User;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void registerUser(UserDTO userDTO) {
        User user = User.builder()
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(User.Role.valueOf(userDTO.getRole().toUpperCase()))
                .build();
        userRepository.save(user);
    }


    public String login(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
            .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return jwtTokenProvider.generateToken(user.getUsername());
    }
}
