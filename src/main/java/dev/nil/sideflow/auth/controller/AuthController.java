package dev.nil.sideflow.auth.controller;

import dev.nil.sideflow.auth.dto.*;
import dev.nil.sideflow.auth.mapper.AuthMapper;
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
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {

        LoginDto loginDto = authMapper.convertToLoginDto(request);
        return authService.loginUser(loginDto);
    }

}
