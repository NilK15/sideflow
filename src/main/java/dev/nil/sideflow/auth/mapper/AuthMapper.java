package dev.nil.sideflow.auth.mapper;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.dto.AuthUserDto;
import dev.nil.sideflow.auth.dto.RegisterRequestResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthUser convertToAuthUser(RegisterRequestResource request);

    @Mapping(target = "roles", expression = "java(authUser.getUserRoles().stream().map(ur -> ur" +
            ".getRole()" +
            ".getName()).toList())")
    AuthUserDto convertToAuthUserDto(AuthUser authUser);
}
