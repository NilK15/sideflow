package dev.nil.sideflow.core.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateUserProfileDto {
    private UUID userId;
    private String email;
}
