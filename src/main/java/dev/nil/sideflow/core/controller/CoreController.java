package dev.nil.sideflow.core.controller;

import dev.nil.sideflow.core.dto.UpdateProfileDto;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileRequest;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import dev.nil.sideflow.core.mapper.CoreMapper;
import dev.nil.sideflow.core.rest.UpdateProfileRequest;
import dev.nil.sideflow.core.service.CoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/core")
@RequiredArgsConstructor
public class CoreController {

    private final CoreService coreService;
    private final CoreMapper coreMapper;

    @GetMapping("/me")
    public UserProfileResponse getUserProfile(@Valid @RequestBody UserProfileRequest request) {

        UserProfileDto userProfile = coreMapper.convertToUserProfile(request);

        return coreService.getUserProfile(userProfile);

    }

    @PatchMapping("/profile")
    public UserProfileDto updateProfile(@Valid @RequestBody UpdateProfileRequest request) {

        UpdateProfileDto updateProfileDto = coreMapper.convertToUpdateProfileDto(request);

        return coreService.updateProfile(updateProfileDto);
    }


}
