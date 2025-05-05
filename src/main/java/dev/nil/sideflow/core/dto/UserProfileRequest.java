package dev.nil.sideflow.core.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequest {
    private String email;
}
