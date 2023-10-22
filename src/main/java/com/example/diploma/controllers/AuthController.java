package com.example.diploma.controllers;

import com.example.diploma.dto.AuthRequest;
import com.example.diploma.dto.AuthResponse;
import com.example.diploma.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.loginUser(authRequest);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("auth-token") String token) {
        authService.logoutUser(token);
    }
}
