package dev.nil.sideflow.auth.service;

import dev.nil.sideflow.auth.domain.model.AuthRole;
import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.domain.model.AuthUserRole;
import dev.nil.sideflow.auth.domain.model.AuthUserRoleId;
import dev.nil.sideflow.auth.domain.repository.AuthRoleRepository;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import dev.nil.sideflow.auth.domain.repository.AuthUserRoleRepository;
import dev.nil.sideflow.auth.dto.AuthUserDto;
import dev.nil.sideflow.auth.dto.LoginDto;
import dev.nil.sideflow.auth.dto.LoginResponse;
import dev.nil.sideflow.auth.mapper.AuthMapper;
import dev.nil.sideflow.common.Constants;
import dev.nil.sideflow.exception.exceptions.RoleNotFoundException;
import dev.nil.sideflow.exception.exceptions.UserAlreadyExistsException;
import dev.nil.sideflow.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthUserRoleRepository authUserRoleRepository;
    private final AuthRoleRepository authRoleRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;


    public AuthUserDto registerUser(AuthUser authUser) {
        // Check if exists
        if (authUserRepository.existsByEmail(authUser.getEmail())) {
            throw new UserAlreadyExistsException(authUser.getEmail(), "Email already registered.");
        }
        // create/save user
        AuthUser user = AuthUser
                .builder()
                .id(UUID.randomUUID()) // 🔥 Set the ID before building the role)
                .email(authUser.getEmail())
                .password(passwordEncoder.encode(authUser.getPassword()))
                .username(authUser.getUsername())
                .build();

        // default role_user role
        AuthRole roleUser = authRoleRepository
                .findByName(Constants.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException(Constants.ROLE_USER, "Default role ROLE_USER" +
                        " not found."));

        AuthUserRole userRole = AuthUserRole.builder()
                                            .id(new AuthUserRoleId(user.getId(), roleUser.getId())) // ✅
                                            // Required
                                            .user(user)
                                            .role(roleUser)
                                            .build();

        user.setUserRoles(Set.of(userRole));

        // assign ROLE_USER to the new user
//        assignRoleToUser(user, roleUser);
//        user.setUserRoles(roleUser.getUserRoles());
        // Why do I need a new one, why does hibernate complain about this being a shared reference?
//        user.setUserRoles(new HashSet<>(roleUser.getUserRoles()));
//        user.setUserRoles(Collections.singletonList(roleUser));

        // save in auth user db
        return authMapper.convertToAuthUserDto(authUserRepository.save(user));

    }


//    public void assignRoleToUser(AuthUser user, AuthRole role) {
//
//        AuthUserRoleId id = new AuthUserRoleId(user.getId(), role.getId());
//        boolean alreadyAssigned = authUserRoleRepository.existsById(id);
//
//        if (alreadyAssigned) {
//            // Optional: throw exception or just return
//            throw new IllegalStateException("User already has this role assigned.");
//        }
//
//        AuthUserRole userRole = AuthUserRole.builder()
//                                            .id(id)
//                                            .user(user)
//                                            .role(role)
//                                            .build();
//        // save to auth user role db
//        authUserRoleRepository.save(userRole);
//    }

    // Don't need a
    public LoginResponse loginUser(LoginDto command) {

        // Ensure user exists in db
        AuthUser user = authUserRepository.findByEmail(command
                                                  .loginRequestResource()
                                                  .getEmail())
                                          .orElseThrow(() -> new IllegalStateException("no user"));

        // jwt implementation to get access token
        String token = authenticateUser(user.getEmail(), user.getPassword());

        return LoginResponse.builder()
                            .token(token)
                            .build();
    }

    public String authenticateUser(String email, String password) {

        // AuthenticationManager created from SecurityConfig class
        // This uses DAOprovider,
        // the usernamePasswordAuthenticationToken is an Authentication, which is what the
        // authenticationmanager.authenticate method takes
        // the dao provider uses a loadByUserName method that I had to define (giving it my auth repository)
        // so it will validate the user based on the password in the db and set that info.
        // This will automatically throw exception if not validated
        Authentication auth =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                        password));

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String userEmail = userDetails.getUsername();

        List<String> roles = userDetails.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .toList();

        return jwtTokenProvider.generateToken(userEmail, roles);
    }

}

