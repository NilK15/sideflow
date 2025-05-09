package dev.nil.sideflow.auth.controller;

import dev.nil.sideflow.auth.dto.AuthUserDto;
import dev.nil.sideflow.auth.dto.LoginRequestDto;
import dev.nil.sideflow.auth.dto.LoginResponseDto;
import dev.nil.sideflow.auth.dto.RegisterDto;
import dev.nil.sideflow.auth.mapper.AuthMapper;
import dev.nil.sideflow.auth.rest.LoginRequest;
import dev.nil.sideflow.auth.rest.RegisterRequest;
import dev.nil.sideflow.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/register")
    public AuthUserDto register(@Valid @RequestBody RegisterRequest request) {
        // Sanitation, incase request has a bunch of other garbage fields coming in from some client -
        // mainly for public apis
        RegisterDto registerDto = authMapper.convertToRegisterDto(request);
        return authService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@Valid @RequestBody LoginRequest request) {

        LoginRequestDto loginRequestDto = authMapper.convertToLoginDto(request);
        return authService.loginUser(loginRequestDto);
    }

}
