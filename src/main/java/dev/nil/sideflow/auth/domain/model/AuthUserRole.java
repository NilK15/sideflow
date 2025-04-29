package dev.nil.sideflow.auth.domain.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "auth_user_role", schema = "auth")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserRole {

    @EmbeddedId
    private AuthUserRoleId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private AuthRole role;
}
