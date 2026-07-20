package com.harish.subscriptiontracker.service;

import com.harish.subscriptiontracker.dto.AuthResponse;
import com.harish.subscriptiontracker.dto.LoginRequest;
import com.harish.subscriptiontracker.dto.RegisterRequest;
import com.harish.subscriptiontracker.dto.UserResponse;
import com.harish.subscriptiontracker.entity.User;
import com.harish.subscriptiontracker.exception.ConflictException;
import com.harish.subscriptiontracker.exception.UnauthorizedException;
import com.harish.subscriptiontracker.repository.UserRepository;
import com.harish.subscriptiontracker.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());

        if (existingUser.isPresent()) {
            throw new ConflictException("User already exists with this email");
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());

        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        user.setPassword(hashedPassword);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(savedUser.getId());
        
        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

        return new AuthResponse(token, userResponse);
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getId());
        
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        return new AuthResponse(token, userResponse);
    }
}
