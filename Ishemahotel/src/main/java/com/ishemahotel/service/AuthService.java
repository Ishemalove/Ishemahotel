package com.ishemahotel.service;

import com.ishemahotel.dto.LoginRequest;
import com.ishemahotel.dto.RegisterRequest;
import com.ishemahotel.dto.UserDto;
import com.ishemahotel.entity.User;
import com.ishemahotel.entity.UserRole;
import com.ishemahotel.repository.UserRepository;
import com.ishemahotel.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserRepository userRepository;

    public UserDto register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // In a real app, you should hash passwords
        user.setPhone(request.getPhone());
        user.setRole(UserRole.CUSTOMER);
        user.setToken(UUID.randomUUID().toString()); // Simple token generation

        return convertToDto(userRepository.save(user));
    }

    public UserDto login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) { // In a real app, use proper password comparison
            throw new ResourceNotFoundException("Invalid email or password");
        }

        user.setToken(UUID.randomUUID().toString()); // Generate new token on login
        return convertToDto(userRepository.save(user));
    }

    public void logout(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid token"));
        user.setToken(null);
        userRepository.save(user);
    }

    public UserDto getCurrentUser(String token) {
        User user = userRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid token"));
        return convertToDto(user);
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setToken(user.getToken());
        return dto;
    }
} 