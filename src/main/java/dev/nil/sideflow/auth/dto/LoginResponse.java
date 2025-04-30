package dev.nil.sideflow.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String accessToken;
//    private String refreshToken; // optional, or you can remove this if not implemented yet
}