package dev.nil.sideflow.auth.mapper;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthUser convertToAuthUser(RegisterDto registerDto);

    @Mapping(target = "roles", expression = "java(authUser.getUserRoles().stream().map(ur -> ur" +
            ".getRole()" +
            ".getName()).toList())")
    AuthUserDto convertToAuthUserDto(AuthUser authUser);

    RegisterDto convertToRegisterDto(RegisterRequest request);

    LoginDto convertToLoginDto(LoginRequest request);
}
