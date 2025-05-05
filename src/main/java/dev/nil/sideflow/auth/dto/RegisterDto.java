package dev.nil.sideflow.auth.dto;

import lombok.Data;

@Data
public class RegisterDto {

    private String email;

    private String password;

    private String username;
}
