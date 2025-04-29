package dev.nil.sideflow.auth.service;

import dev.nil.sideflow.auth.domain.model.AuthRole;
import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.domain.model.AuthUserRole;
import dev.nil.sideflow.auth.domain.model.AuthUserRoleId;
import dev.nil.sideflow.auth.domain.repository.AuthRoleRepository;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import dev.nil.sideflow.auth.domain.repository.AuthUserRoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthUserRoleRepository authUserRoleRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder; // we'll need this soon

    public AuthUser registerUser(String email, String password) {
        // Check if exists
        if (authUserRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already registered.");
        }
        // create/save user
        AuthUser user = AuthUser.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();

        // save in auth user db
        authUserRepository.save(user);

        //get default role_user role
        AuthRole roleUser = authRoleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("Default role ROLE_USER not found."));

        // 4. Assign ROLE_USER to the new user
        assignRoleToUser(user, roleUser);

        return user;
    }

    public void assignRoleToUser(AuthUser user, AuthRole role) {

        AuthUserRoleId id = new AuthUserRoleId(user.getId(), role.getId());
        boolean alreadyAssigned = authUserRoleRepository.existsById(id);

        if (alreadyAssigned) {
            // Optional: throw exception or just return
            throw new IllegalStateException("User already has this role assigned.");
        }

        AuthUserRole userRole = AuthUserRole.builder()
                .id(id)
                .user(user)
                .role(role)
                .build();

        // save to auth user role db
        authUserRoleRepository.save(userRole);
    }
}

