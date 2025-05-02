package dev.nil.sideflow.auth.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "auth_user", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUser {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthUserRole> userRoles;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuthRefreshToken> refreshTokens;
}
