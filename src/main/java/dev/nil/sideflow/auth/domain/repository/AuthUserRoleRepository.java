package dev.nil.sideflow.auth.domain.repository;

import dev.nil.sideflow.auth.domain.entity.AuthUserRole;
import dev.nil.sideflow.auth.domain.entity.AuthUserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRoleRepository extends JpaRepository<AuthUserRole, AuthUserRoleId> {
}
