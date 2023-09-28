package de.neuefische.studentdbbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(
                appUser.username(),
                appUser.password(),
                List.of(new SimpleGrantedAuthority("ROLE_" + appUser.role()))
        );
    }

    public AppUserResponse register(NewAppUser newAppUser){

        authenticationManager.authenticate(new User(
                newAppUser.username(),
                newAppUser.password(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        ));
    }


}
