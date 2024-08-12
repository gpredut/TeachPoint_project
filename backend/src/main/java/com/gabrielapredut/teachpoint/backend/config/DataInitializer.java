package com.gabrielapredut.teachpoint.backend.config;

import com.gabrielapredut.teachpoint.backend.model.ERole;
import com.gabrielapredut.teachpoint.backend.model.Role;
import com.gabrielapredut.teachpoint.backend.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner dataLoader(RoleRepository roleRepository) {
        return args -> {
            // Check if roles already exist to prevent duplicate entries
            if (roleRepository.count() == 0) {
                // Insert roles
                roleRepository.save(new Role(ERole.ADMIN));
                roleRepository.save(new Role(ERole.INSTRUCTOR));
                roleRepository.save(new Role(ERole.STUDENT));
            }
        };
    }
}
