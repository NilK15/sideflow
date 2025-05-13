package dev.nil.sideflow.core.controller;

import dev.nil.sideflow.core.dto.UpdateProfileDto;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import dev.nil.sideflow.core.mapper.CoreMapper;
import dev.nil.sideflow.core.rest.UpdateProfileRequest;
import dev.nil.sideflow.core.service.CoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/core")
@RequiredArgsConstructor
public class CoreController {

    private final CoreService coreService;
    private final CoreMapper coreMapper;

    @PreAuthorize("hasAnyRole('ROLE_FREELANCER', 'ROLE_CLIENT')")
    @GetMapping("/me")
    public UserProfileResponse getUserProfile() {

        return coreService.getUserProfileById();
    }

    @PreAuthorize("hasAnyRole('ROLE_FREELANCER', 'ROLE_CLIENT')")
    @PatchMapping("/profile")
    public UserProfileDto updateProfile(@Valid @RequestBody UpdateProfileRequest request) {

        UpdateProfileDto updateProfileDto = coreMapper.convertToUpdateProfileDto(request);
        return coreService.updateProfile(updateProfileDto);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/client/dashboard")
    public void getClientDashboard() {
        //Dashboard logic
    }

    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    @GetMapping("/freelancer/dashboard")
    public void getFreelancerDashboard() {
        //Dashboard logic
    }


}
