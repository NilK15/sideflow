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

    /**
     * using many to one annotations with multiple tables grants flexibility for me to add other fields
     * later. However, I have to manually update this table using code.
     * <p>
     * If I use manytomany annotations, then I wouldn't have to manually update table with code, but
     * the table would be invisible to me so I couldn't add custom fields like grantedby, timedate, etc.
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private AuthUser user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private AuthRole role;
}
