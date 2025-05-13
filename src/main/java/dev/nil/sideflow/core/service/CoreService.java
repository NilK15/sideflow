package dev.nil.sideflow.core.service;

import dev.nil.sideflow.auth.domain.entity.AuthUser;
import dev.nil.sideflow.core.dto.UpdateProfileDto;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileResponse;

public interface CoreService {

    UserProfileResponse getUserProfileById();

    void createDefaultUserProfile(AuthUser authUser);

    UserProfileDto updateProfile(UpdateProfileDto updateProfileDto);

    String getAuthenticatedUserId();

}
