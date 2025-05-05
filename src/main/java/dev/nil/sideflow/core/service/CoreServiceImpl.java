package dev.nil.sideflow.core.service;

import dev.nil.sideflow.auth.domain.model.AuthUser;
import dev.nil.sideflow.core.domain.entity.UserProfile;
import dev.nil.sideflow.core.domain.repository.UserProfileRepository;
import dev.nil.sideflow.core.dto.UserProfileDto;
import dev.nil.sideflow.core.dto.UserProfileResponse;
import dev.nil.sideflow.core.mapper.CoreMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
@Transactional
public class CoreServiceImpl implements CoreService {

    private final UserProfileRepository userProfileRepository;
    //    private final AuthUserRepository authUserRepository;
    private final CoreMapper coreMapper;

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
                                             .build();

        userProfileRepository.save(userProfile);

    }
}
