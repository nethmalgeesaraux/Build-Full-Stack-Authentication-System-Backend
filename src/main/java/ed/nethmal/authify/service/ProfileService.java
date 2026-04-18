package ed.nethmal.authify.service;

import ed.nethmal.authify.io.ProfileRequest;
import ed.nethmal.authify.io.ProfileResponse;

public interface ProfileService {

    ProfileResponse createProfile(ProfileRequest request);

    ProfileResponse getProfile(String email);

    void sendRestOtp(String email);
}
