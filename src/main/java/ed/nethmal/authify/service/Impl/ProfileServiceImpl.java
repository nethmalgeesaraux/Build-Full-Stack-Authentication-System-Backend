package ed.nethmal.authify.service.Impl;

import ed.nethmal.authify.entity.UserEntity;
import ed.nethmal.authify.io.UserRequest;
import ed.nethmal.authify.io.UserResponse;
import ed.nethmal.authify.repository.UserRepostory;
import ed.nethmal.authify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepostory userRepostory;

    @Override
    public UserResponse createProfile(UserRequest request) {
        UserEntity newProfile = convertToUserEntity(request);
        newProfile = userRepostory.save(newProfile);
        return convertToUserResponse(newProfile);
    }

    private UserResponse convertToUserResponse(UserEntity newProfile) {
        return UserResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(UserRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(request.getPassword())
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
       }
       

}
