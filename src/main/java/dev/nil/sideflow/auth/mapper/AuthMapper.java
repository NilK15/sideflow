package dev.nil.sideflow.auth.mapper;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.auth.dto.RegisterRequestResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    AuthUser convertToAuthUser(RegisterRequestResource request);
}
