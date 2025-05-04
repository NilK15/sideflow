package dev.nil.sideflow.auth.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record AuthUserDto(UUID id, String username, String email, List<String> roles) {
}
