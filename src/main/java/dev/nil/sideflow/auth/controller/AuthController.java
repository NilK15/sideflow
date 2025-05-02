package dev.nil.sideflow.auth.controller;

import dev.nil.sideflow.auth.domain.command.LoginCommand;
import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.dto.LoginRequestResource;
import dev.nil.sideflow.auth.dto.LoginResponse;
import dev.nil.sideflow.auth.dto.RegisterRequestResource;
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
    public AuthUser register(@Valid @RequestBody RegisterRequestResource request) {

        AuthUser authUser = authMapper.convertToAuthUser(request);

        return authService.registerUser(authUser);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequestResource request) {

        LoginCommand loginCommand = LoginCommand.builder()
                                                .loginRequestResource(request)
                                                .build();

        return authService.loginUser(loginCommand);
    }

}
