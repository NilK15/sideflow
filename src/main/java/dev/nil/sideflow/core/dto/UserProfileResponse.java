package dev.nil.sideflow.core.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileResponse {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String notifications;
    private Boolean isPremium;
}
