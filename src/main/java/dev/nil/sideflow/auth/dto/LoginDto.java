package dev.nil.sideflow.auth.dto;

import lombok.Builder;

// Not really needed, but isolates and create pure unit testing (loginrequest resource has validations, etc)
@Builder
public record LoginDto(LoginRequest loginRequest) {
}