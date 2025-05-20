package com.ishemahotel.dto;

import com.ishemahotel.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private UserRole role;
    private String token;
} 