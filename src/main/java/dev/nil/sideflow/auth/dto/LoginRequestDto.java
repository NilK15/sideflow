package dev.nil.sideflow.auth.dto;

// Not really needed, but isolates and create pure unit testing (loginrequest resource has validations, etc)
public record LoginRequestDto(String email, String password) {
}