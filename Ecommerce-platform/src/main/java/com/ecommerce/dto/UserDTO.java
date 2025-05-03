package com.ecommerce.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private String role;
}
