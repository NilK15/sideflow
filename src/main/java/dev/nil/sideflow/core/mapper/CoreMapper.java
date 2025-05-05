package dev.nil.sideflow.core.mapper;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.core.domain.entity.UserProfile;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileRequest;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CoreMapper {

    UserProfileDto convertToUserProfile(UserProfileRequest request);

    UserProfileResponse convertToUserProfileResponse(UserProfile userProfile);

    default AuthUser mapToAuthUser(UUID userId) {
        return AuthUser.builder()
                       .id(userId)
                       .build();
    }
}
