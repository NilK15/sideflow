package dev.nil.sideflow.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String notifications;
    private Boolean isPremium;
}
