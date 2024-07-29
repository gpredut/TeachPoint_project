package com.gabrielapredut.teachpoint.backend.service;

import com.gabrielapredut.teachpoint.backend.model.Role;
import com.gabrielapredut.teachpoint.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }
}
