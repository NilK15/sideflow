package dev.nil.sideflow.auth.controller;

import dev.nil.sideflow.auth.domain.model.AuthUser;
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
    public AuthUserDto register(@Valid @RequestBody RegisterRequestResource request) {

        AuthUser authUser = authMapper.convertToAuthUser(request);

        return authService.registerUser(authUser);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequestResource request) {

        // Do this or use a mapper, whats better?
        LoginDto loginDto = LoginDto.builder()
                                    .loginRequestResource(request)
                                    .build();

        return authService.loginUser(loginDto);
    }
}
