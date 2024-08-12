package com.gabrielapredut.teachpoint.backend.repository;

import com.gabrielapredut.teachpoint.backend.model.ERole;
import com.gabrielapredut.teachpoint.backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ERole roleName);
}
