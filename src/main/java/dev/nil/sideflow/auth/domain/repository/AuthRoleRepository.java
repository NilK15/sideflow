package dev.nil.sideflow.auth.domain.repository;

import dev.nil.sideflow.auth.domain.model.AuthRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthRoleRepository extends JpaRepository<AuthRole, UUID> {

    Optional<AuthRole> findByName(String name);
}