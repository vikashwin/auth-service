package com.vikash.auth_service.repository;

import com.vikash.auth_service.entity.Role;
import com.vikash.auth_service.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);

    boolean existsByName(UserRole name);
}
