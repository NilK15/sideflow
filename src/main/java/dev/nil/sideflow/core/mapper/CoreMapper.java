package dev.nil.sideflow.core.mapper;

import dev.nil.sideflow.auth.domain.entity.AuthUser;
import dev.nil.sideflow.core.domain.entity.UserProfile;
import dev.nil.sideflow.core.dto.UpdateProfileDto;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileRequest;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import dev.nil.sideflow.core.rest.UpdateProfileRequest;
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

    UpdateProfileDto convertToUpdateProfileDto(UpdateProfileRequest request);

    UserProfile convertToUserProfile(UpdateProfileDto updateProfileDto);

    UserProfileDto convertToUserProfileDto(UserProfile userProfile);
}
