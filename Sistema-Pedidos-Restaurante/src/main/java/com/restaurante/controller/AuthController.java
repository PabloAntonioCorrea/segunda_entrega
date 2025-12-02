package com.restaurante.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações de autenticação")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Realiza login no sistema")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        if (request.getUsername() != null && request.getPassword() != null && 
            request.getPassword().length() >= 3) {
            String token = "mock_token_" + System.currentTimeMillis();
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(request.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/register")
    @Operation(summary = "Registro", description = "Registra um novo usuário no sistema")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        if (request.getUsername() != null && request.getPassword() != null && 
            request.getPassword().length() >= 3) {
            // Validação básica de email
            if (!request.getUsername().contains("@")) {
                return ResponseEntity.badRequest().build();
            }
            
            String token = "mock_token_" + System.currentTimeMillis();
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsername(request.getUsername());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterRequest {
        private String username;
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginResponse {
        private String token;
        private String username;
    }
}

