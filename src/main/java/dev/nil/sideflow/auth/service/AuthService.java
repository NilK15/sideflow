package dev.nil.sideflow.auth.service;

import dev.nil.sideflow.auth.domain.model.AuthRole;
import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.domain.model.AuthUserRole;
import dev.nil.sideflow.auth.domain.model.AuthUserRoleId;
import dev.nil.sideflow.auth.domain.repository.AuthRoleRepository;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import dev.nil.sideflow.auth.dto.AuthUserDto;
import dev.nil.sideflow.auth.dto.LoginDto;
import dev.nil.sideflow.auth.dto.LoginResponse;
import dev.nil.sideflow.auth.dto.RegisterDto;
import dev.nil.sideflow.auth.mapper.AuthMapper;
import dev.nil.sideflow.common.Constants;
import dev.nil.sideflow.core.dto.CreateUserProfileDto;
import dev.nil.sideflow.core.service.CoreService;
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

    private final AuthRoleRepository authRoleRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthMapper authMapper;
    private final CoreService coreService;


    public AuthUserDto registerUser(RegisterDto registerDto) {

        AuthUser authUser = authMapper.convertToAuthUser(registerDto);

        // Check if the user already exists via email
        if (authUserRepository.existsByEmail(authUser.getEmail())) {
            throw new UserAlreadyExistsException(authUser.getEmail(), "Email already registered.");
        }

        // Creates a new user for auth_user table
        AuthUser user = AuthUser
                .builder()
                .id(UUID.randomUUID())
                .email(authUser.getEmail())
                .password(passwordEncoder.encode(authUser.getPassword()))
                .username(authUser.getUsername())
                .build();

        // Creates a new role by retrieving from db to assign to newly registered users (ROLE_USER)
        AuthRole role = authRoleRepository
                .findByName(Constants.ROLE_USER)
                .orElseThrow(() -> new RoleNotFoundException(Constants.ROLE_USER, "Default role ROLE_USER" +
                        " not found."));

        //Creates new AuthUserRole based on above user and role to store in auth_user_role table that has
        // relation with user and role
        AuthUserRole userRole = AuthUserRole.builder()
                                            .id(AuthUserRoleId.builder()
                                                              .userId(user.getId())
                                                              .roleId(role.getId())
                                                              .build())
                                            .user(user)
                                            .role(role)
                                            .build();

        /* Hibernate doesn't want shared references of lists being used in other entities, so have to create
         a unique one
        */
        // Assigning the authUserRole relationship to authUser
        user.setUserRoles(Set.of(userRole));


        // Saves in auth_user db and auth_user_role table (via cascading)
        // Also using this savedUser vs a DTO is better, as it is the instance managed by Hibernate, and
        // it wants
        // just one entity being passed around - a dto being a passed around is going to need to be accessed
        // to retrieve the auth user from db anyway.
        AuthUser savedUser = authUserRepository.save(user);
        AuthUserDto authUserDto = authMapper.convertToAuthUserDto(savedUser);

        // Create a new user profile to pass to coreService to store in user_profile db
        CreateUserProfileDto createUserProfileDto = CreateUserProfileDto.builder()
                                                                        .userId(user.getId())
                                                                        .email(authUser.getEmail())
                                                                        .build();
        // Saves minimum info in user_profile table
        coreService.createDefaultUserProfile(savedUser);


        return authUserDto;
    }

    public LoginResponse loginUser(LoginDto loginDto) {

        // Ensure user exists in db
        authUserRepository.findByEmail(loginDto
                                  .email())
                          .orElseThrow(() -> new IllegalStateException("User doesn't exist"));

        // jwt implementation to get access token
        String token = authenticateUser(
                loginDto.email(),
                loginDto.password());

        return LoginResponse.builder()
                            .token(token)
                            .build();
    }

    public String authenticateUser(String email, String password) {

        /* AuthenticationManager created from SecurityConfig class
         This uses DAOprovider,
         the usernamePasswordAuthenticationToken is an Authentication, which is what the
         authenticationmanager.authenticate method takes
         the dao provider uses a loadByUserName method that I had to define (giving it my auth repository)
         so it will validate the user based on the password in the db and set that info.
         This will automatically throw exception if not validated
         */

        // authenticates with db password hash
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

