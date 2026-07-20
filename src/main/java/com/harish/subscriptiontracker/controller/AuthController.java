package com.harish.subscriptiontracker.controller;

import com.harish.subscriptiontracker.dto.AuthResponse;
import com.harish.subscriptiontracker.dto.LoginRequest;
import com.harish.subscriptiontracker.dto.RegisterRequest;
import com.harish.subscriptiontracker.response.ApiResponse;
import com.harish.subscriptiontracker.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse result = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("New user successfully created", result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse result = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("User successfully logged in", result));
    }
}
