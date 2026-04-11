package ed.nethmal.authify.service;

import ed.nethmal.authify.io.UserRequest;
import ed.nethmal.authify.io.UserResponse;

public interface ProfileService {

    UserResponse createProfile(UserRequest request);
}
