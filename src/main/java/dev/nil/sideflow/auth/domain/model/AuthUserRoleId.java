package dev.nil.sideflow.auth.domain.model;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserRoleId implements Serializable {

    private UUID userId;
    private UUID roleId;
}