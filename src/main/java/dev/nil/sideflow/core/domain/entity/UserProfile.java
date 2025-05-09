package dev.nil.sideflow.core.domain.entity;

import dev.nil.sideflow.auth.domain.entity.AuthUser;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Data
@Entity
@Table(name = "user_profile", schema = "core")
@RequiredArgsConstructor
@AllArgsConstructor
public class UserProfile {

    @Id
    private UUID id; // shared PK and FK
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "notifications_preferences")
    private String notifications;
    @Column(name = "is_premium")
    private Boolean isPremium;

    @OneToOne
    @MapsId // <- this is key to use same ID from AuthUser
    @JoinColumn(name = "id") // FK to auth.auth_user(id)
    private AuthUser authUser;

}
