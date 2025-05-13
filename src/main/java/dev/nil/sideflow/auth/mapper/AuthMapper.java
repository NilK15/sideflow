package dev.nil.sideflow.auth.mapper;

import dev.nil.sideflow.auth.domain.UserType;
import dev.nil.sideflow.auth.domain.entity.AuthUser;
import dev.nil.sideflow.auth.dto.AuthUserDto;
import dev.nil.sideflow.auth.dto.LoginRequestDto;
import dev.nil.sideflow.auth.dto.RegisterDto;
import dev.nil.sideflow.auth.rest.LoginRequest;
import dev.nil.sideflow.auth.rest.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = UserType.class)
public interface AuthMapper {

    @Mapping(target = "userType", expression = "java(registerDto.getUserType())")
    AuthUser convertToAuthUser(RegisterDto registerDto);

    @Mapping(target = "roles", expression = "java(authUser.getUserRoles().stream().map(ur -> ur" +
            ".getRole()" +
            ".getName()).toList())")
    AuthUserDto convertToAuthUserDto(AuthUser authUser);

    RegisterDto convertToRegisterDto(RegisterRequest request);

    LoginRequestDto convertToLoginDto(LoginRequest request);
}
