package dev.nil.sideflow.auth.domain.repository;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface AuthUserRepository extends JpaRepository<AuthUser, UUID> {

    boolean existsByEmail(String email);

    // lazy loading, doesn't actually get data until accessed
    Optional<AuthUser> findByEmail(String email);

    // Need this query to eagerly fetch data, as findByEmail() is lazy
    @Query("""
                SELECT u FROM AuthUser u
                LEFT JOIN FETCH u.userRoles ur
                LEFT JOIN FETCH ur.role
                WHERE u.email = :email
            """)
    Optional<AuthUser> findByEmailWithRoles(String email);
}