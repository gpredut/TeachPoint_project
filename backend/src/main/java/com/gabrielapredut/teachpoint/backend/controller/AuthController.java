package com.gabrielapredut.teachpoint.backend.controller;

import com.gabrielapredut.teachpoint.backend.model.User;
import com.gabrielapredut.teachpoint.backend.repository.UserRepository;
import com.gabrielapredut.teachpoint.backend.security.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Check if username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        // Encrypt password and save user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        // Validate user credentials
        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser != null && passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            // Generate JWT token
            String token = jwtUtility.generateToken(existingUser.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
