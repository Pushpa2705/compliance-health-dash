package com.internship.tool.service;

import com.internship.tool.entity.User;
import com.internship.tool.repository.UserRepository;
import com.internship.tool.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepo, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
    }

    public User register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole("VIEWER");
        return userRepo.save(user);
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(username);
    }

    public String refresh(String token) {
        String username = jwtUtil.extractUsername(token);
        return jwtUtil.generateToken(username);
    }
}