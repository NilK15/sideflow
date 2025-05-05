package dev.nil.sideflow.auth.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RegisterDto {
    private String email;
    private String password;
    private String username;
}
