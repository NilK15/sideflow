package dev.nil.sideflow.core.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateUserProfileDto(UUID userId, String email) {
}
