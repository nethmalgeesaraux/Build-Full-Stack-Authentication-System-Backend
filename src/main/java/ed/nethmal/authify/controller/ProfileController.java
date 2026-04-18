package ed.nethmal.authify.controller;

import ed.nethmal.authify.io.ProfileRequest;
import ed.nethmal.authify.io.ProfileResponse;
import ed.nethmal.authify.service.EmailService;
import ed.nethmal.authify.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse register(@Valid @RequestBody ProfileRequest request) {
        emailService.sendWelcomeEmail(request.getEmail(), request.getName());
        return profileService.createProfile(request);
    }

    @GetMapping("/test")
    public String test(){
        return "Profile controller is working!";
    }

    @GetMapping
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication.name") String email) {
        return profileService.getProfile(email);

    }
}
