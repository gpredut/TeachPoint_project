package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.ERole;
import com.gabrielapredut.teachpoint.backend.model.Role;
import com.gabrielapredut.teachpoint.backend.model.User;
import com.gabrielapredut.teachpoint.backend.repository.RoleRepository;
import com.gabrielapredut.teachpoint.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public String registerUser(User user, String roleName) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "Error: Username is already taken!";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            return "Error: Email is already in use!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assign role based on user input or default to STUDENT
        Set<Role> roles = new HashSet<>();
        ERole role;
        try {
            role = ERole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Error: Role is not valid.";
        }

        Role userRole = roleRepository.findByName(role)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        userRepository.save(user);

        // Log successful registration
        logger.info("User registered: {}", user.getUsername());

        return "User registered successfully";
    }

    public String loginUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return "Login successful";
        }
        return "Invalid username or password";
    }
}
