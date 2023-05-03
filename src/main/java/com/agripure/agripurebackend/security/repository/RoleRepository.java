package com.agripure.agripurebackend.security.repository;

import com.agripure.agripurebackend.security.entity.Role;
import com.agripure.agripurebackend.security.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleName roleName);
}
