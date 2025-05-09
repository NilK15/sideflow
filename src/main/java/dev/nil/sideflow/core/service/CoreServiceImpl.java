package dev.nil.sideflow.core.service;

import dev.nil.sideflow.auth.domain.entity.AuthUser;
import dev.nil.sideflow.auth.domain.repository.AuthUserRepository;
import dev.nil.sideflow.core.domain.entity.UserProfile;
import dev.nil.sideflow.core.domain.repository.UserProfileRepository;
import dev.nil.sideflow.core.dto.UpdateProfileDto;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import dev.nil.sideflow.core.mapper.CoreMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreServiceImpl implements CoreService {

    private final UserProfileRepository userProfileRepository;
    //    private final AuthUserRepository authUserRepository;
    private final CoreMapper coreMapper;
    private final AuthUserRepository authUserRepository;

    Logger log = getLogger(CoreServiceImpl.class);


    @Override
    public UserProfileResponse getUserProfile(UserProfileDto userProfileDto) {

        UserProfile userProfile = userProfileRepository.findByEmail(userProfileDto.getEmail())
                                                       .orElseThrow(() -> new UsernameNotFoundException(
                                                               "Username not found"));

        return coreMapper.convertToUserProfileResponse(userProfile);

    }

    @Override
    public void createDefaultUserProfile(AuthUser authUser) {
        log.info("Creating user profile for {}", authUser.getId());

        UserProfile userProfile = UserProfile.builder()
                                             .authUser(authUser)
                                             .email(authUser.getEmail())
                                             .username(authUser.getUsername())
                                             .build();

        userProfileRepository.save(userProfile);

    }

    @Override
    public UserProfileDto updateProfile(UpdateProfileDto updateProfileDto) {
        log.info("Updating profile");

        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();

        String userId = authentication.getName();
        UUID userUuid = UUID.fromString(userId);

        AuthUser authUser = authUserRepository.findById(userUuid)
                                              .orElseThrow(() -> new UsernameNotFoundException("User not " +
                                                      "found"));

        UserProfile userProfile = userProfileRepository.findById(userUuid)
                                                       .orElseThrow(() -> new IllegalArgumentException(
                                                               "Profile not found"));

        userProfile.setAuthUser(authUser);

        if (updateProfileDto.getFirstName() != null) {
            userProfile.setFirstName(updateProfileDto.getFirstName());
        }
        if (updateProfileDto.getLastName() != null) {
            userProfile.setLastName(updateProfileDto.getLastName());
        }
        if (updateProfileDto.getNotifications() != null) {
            userProfile.setNotifications(updateProfileDto.getNotifications());
        }
        if (updateProfileDto.getIsPremium() != null) {
            userProfile.setIsPremium(updateProfileDto.getIsPremium());
        }


        return coreMapper.convertToUserProfileDto(userProfileRepository.save(userProfile));
    }
}
