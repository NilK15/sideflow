package dev.nil.sideflow.core.rest;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UpdateProfileRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String notification;
    private Boolean isPremium;
}
