package ed.nethmal.authify.service;

import ed.nethmal.authify.entity.UserEntity;
import ed.nethmal.authify.repository.UserRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepostory userRepostory;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       UserEntity existingUser = userRepostory.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

       return new User(existingUser.getEmail(), existingUser.getPassword() , new ArrayList<>());
    }
}
