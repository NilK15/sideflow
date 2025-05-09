package dev.nil.sideflow.security;

import dev.nil.sideflow.auth.domain.entity.AuthUser;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthUserRepository authUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = resolveToken(request);

        // We let spring continue through filter to handle error handling to return 401 unauthorized, if
        // we throw exception then we have to handle it
        if (token == null || !jwtTokenProvider.isTokenValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        UUID id = UUID.fromString(jwtTokenProvider.getSubject(token));
        List<String> roles = jwtTokenProvider.getRoles(token);

        Optional<AuthUser> userOptional = authUserRepository.findById(id);

        if (userOptional.isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        AuthUser user = userOptional.get();

        List<GrantedAuthority> authorities = roles.stream()
                                                  .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role))
                                                  .toList();

//        List<GrantedAuthority> authorities = user.getUserRoles()
//                                                 .stream()
//                                                 // have to cast this to GrantedAuthority due to genrics
//                                                 // limitation for type safety (streams uses generics)
//                                                 .map(role -> (GrantedAuthority) new
//                                                 SimpleGrantedAuthority
//                                                         ("ROLE_" + role.getRole()
//                                                                        .getName()))
//                                                 .toList();
//
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(id, null, authorities);

        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        filterChain.doFilter(request, response);


    }

    private String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }
}
