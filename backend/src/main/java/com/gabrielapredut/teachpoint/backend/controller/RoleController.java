package com.gabrielapredut.teachpoint.backend.controller;

import com.gabrielapredut.teachpoint.backend.model.User;
import com.gabrielapredut.teachpoint.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/assign")
    public ResponseEntity<?> assignRole(@RequestParam String username, @RequestParam String role) {
        // Find user by username
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Assign role to user
        Set<String> roles = user.getRoles() == null ? new HashSet<>() : user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Role assigned");
    }
}
