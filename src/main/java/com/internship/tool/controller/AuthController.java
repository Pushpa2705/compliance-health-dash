package com.internship.tool.controller;

import com.internship.tool.entity.User;
import com.internship.tool.service.AuthService;
import com.internship.tool.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService service;
    private final JwtUtil jwtUtil;   

    public AuthController(AuthService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        String token = service.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/refresh")
public ResponseEntity<?> refreshToken(@RequestHeader(value = "Authorization", required = false) String header) {

    System.out.println("HEADER: [" + header + "]");

    if (header == null) {
        return ResponseEntity.badRequest().body("Missing Authorization header");
    }

    
    String token = header.replace("Bearer ", "").trim();

    try {
        String username = jwtUtil.extractUsername(token);

        if (jwtUtil.isTokenExpired(token)) {
            return ResponseEntity.status(401).body("Token expired");
        }

        String newToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok(Map.of("token", newToken));

    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Invalid token format");
    }
}
}