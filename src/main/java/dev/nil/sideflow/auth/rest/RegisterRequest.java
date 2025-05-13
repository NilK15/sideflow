package dev.nil.sideflow.auth.rest;

import dev.nil.sideflow.auth.domain.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {

    @Email(message = "Email is not valid.")
    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotNull(message = "User type is required")
    private UserType userType;
}
