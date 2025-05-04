package dev.nil.sideflow.auth.service;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.domain.model.AuthUserRole;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link UserDetailsService} to load user-specific data for authentication.
 * <p>
 * This service integrates with the {@link AuthUserRepository} to fetch user details based on the
 * provided username (email). The user details are then converted into a {@link UserDetails} object
 * for use with Spring Security.
 * <p>
 * Defining with @Service is what helps spring-security know which provider to provide as it looks for
 * UserDetailService type class and also PasswordEncoder (defined as bean already in SecurityConfig)
 */
@RequiredArgsConstructor
@Service
public class CustomerUserDetailService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;

    // Need to return UserDetails object for DAOProvider to use this to ensure
    // 1. authentication (compares password, using password encoder to ensure hash matches)
    // 2. load roles
    // 3. load username for identity (email in our case)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findByEmail(username)
                                          .orElseThrow(() -> new UsernameNotFoundException("User not " +
                                                  "found"));
        return new User(
                user.getEmail(),
                user.getPassword(),
                user.getUserRoles()
                    .stream()
                    .map(AuthUserRole::getRole)
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .toList()
        );
    }
}
