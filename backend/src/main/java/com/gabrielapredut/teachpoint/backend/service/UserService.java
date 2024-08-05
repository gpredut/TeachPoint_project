package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.ERole;
import com.gabrielapredut.teachpoint.backend.model.Role;
import com.gabrielapredut.teachpoint.backend.model.User;
import com.gabrielapredut.teachpoint.backend.model.UserRegistrationRequest;
import com.gabrielapredut.teachpoint.backend.repository.RoleRepository;
import com.gabrielapredut.teachpoint.backend.repository.UserRepository;
import com.gabrielapredut.teachpoint.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil; // Add JwtUtil dependency

    public void registerUser(UserRegistrationRequest request) {
        // Convert role name from String to ERole
        ERole roleEnum = getERoleFromString(request.getRoleName());

        // Check if the role exists
        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        // Create new User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password
        user.setName(request.getName());
        user.setSurname(request.getSurname());
        user.setEmail(request.getEmail());

        // Assign role to user
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        // Save the user
        userRepository.save(user);
    }

    private ERole getERoleFromString(String roleName) {
        try {
            return ERole.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Role not found: " + roleName);
        }
    }

    public String loginUser(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent() && passwordEncoder.matches(user.getPassword(), existingUser.get().getPassword())) {
            return "Login successful";
        }
        return "Invalid username or password";
    }

    public String createToken(String username) {
        return jwtUtil.createToken(username);
    }
}

