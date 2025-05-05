package dev.nil.sideflow.core.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileResponse {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String notification;
    private String isPremium;
}
