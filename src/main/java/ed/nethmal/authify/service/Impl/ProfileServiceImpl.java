package ed.nethmal.authify.service.Impl;

import ed.nethmal.authify.entity.UserEntity;
import ed.nethmal.authify.io.ProfileRequest;
import ed.nethmal.authify.io.ProfileResponse;
import ed.nethmal.authify.repository.UserRepostory;
import ed.nethmal.authify.service.EmailService;
import ed.nethmal.authify.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepostory userRepostory;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        UserEntity newProfile = convertToUserEntity(request);

        if(!userRepostory.existsByEmail(request.getEmail())) {
            newProfile = userRepostory.save(newProfile);
            return convertToUserResponse(newProfile);

        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");


    }

    @Override
    public ProfileResponse getProfile(String email) {
        UserEntity existingUser = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return convertToUserResponse(existingUser);
    }

    @Override
    public void sendRestOtp(String email) {
        UserEntity existingEntity = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        //Generate 6 digit otp
                String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        //calculate expiry time (current time + 15 minutes in milliseconds)
                long expiryTime = System.currentTimeMillis() + (15 * 60 * 1000);

        //update the profile/user
                existingEntity.setResetOtp(otp);
                existingEntity.setResetOtpExpireAt(expiryTime);

        //save into the database
        userRepostory.save(existingEntity);

        try {
            emailService.sendResetOtpEmail(existingEntity.getEmail(), otp);
        }catch (Exception e) {
            throw new RuntimeException("Unable to send email");
        }

    }

    @Override
    public void restPassword(String email, String otp, String newPassword) {
        UserEntity existingEntity = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if(existingEntity.getResetOtp() == null || !existingEntity.getResetOtp().equals(otp)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        if(System.currentTimeMillis() > existingEntity.getResetOtpExpireAt()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP has expired");
        }

        existingEntity.setPassword(passwordEncoder.encode(newPassword));
        existingEntity.setResetOtp(null);
        existingEntity.setResetOtpExpireAt(0L);

        userRepostory.save(existingEntity);
    }

    @Override
    public void sendOtp(String email) {
        UserEntity existingEntity = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if(existingEntity.getIsAccountVerified() != null && existingEntity.getIsAccountVerified()) {
            return;
        }

        //Generate 6 digit otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));

        //calculate expiry time (current time + 24 h in milliseconds)
        long expiryTime = System.currentTimeMillis() + (24 * 60 * 60 * 1000);

        existingEntity.setVerifyOtp(otp);
        existingEntity.setVerifyOtpExpireAt(expiryTime);

        //Save
        userRepostory.save(existingEntity);


    }

    @Override
    public void verifyOtp(String email, String otp) {


    }

    @Override
    public String getLoggedInUserId(String email) {
        UserEntity existingEntity = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return existingEntity.getUserId();
    }


    private ProfileResponse convertToUserResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtp(null)
                .build();
    }


}
