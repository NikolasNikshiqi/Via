package org.spring.via.controllers;

import org.spring.via.Enums.Role;
import org.spring.via.Models.User;
import org.spring.via.Repositories.UserRepo;
import org.spring.via.config.DTOs;
import org.spring.via.config.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login (@RequestBody DTOs.LoginCredentials loginCredentials) {
        User user = userRepo.findByEmail(loginCredentials.email()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        if(passwordEncoder.matches(loginCredentials.password(), user.getPassword())){
            return jwtUtil.generateToken(user.getEmail());
        }else {
            throw new RuntimeException("Wrong credentials");
        }

    }

    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody DTOs.SignUpCredentials credentials) {

        userRepo.findByEmail(credentials.email()).ifPresent( user -> new RuntimeException("Email already in use"));

        User user = new User(
                credentials.username(),
                credentials.email(),
                passwordEncoder.encode(credentials.password()),
                Role.USER
        );

        userRepo.save(user);
        return ResponseEntity.ok().build();
    }
}
