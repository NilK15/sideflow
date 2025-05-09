package dev.nil.sideflow.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponseDto {
    private String token;
//    private String refreshToken; // optional, or you can remove this if not implemented yet
}