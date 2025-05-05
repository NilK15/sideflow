package dev.nil.sideflow.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class AuthUserDto {
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;
}
