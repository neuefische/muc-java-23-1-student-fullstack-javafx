package de.neuefische.studentdbbackend.auth;

import de.neuefische.studentdbbackend.security.AppUser;
import de.neuefische.studentdbbackend.security.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class AuthBeans {

    private final AppUserRepository appUserRepository;

    @Bean
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return username -> {
            AppUser appUser = appUserRepository.findAppUserByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            return new User(
                    appUser.username(),
                    appUser.password(),
                    List.of(new SimpleGrantedAuthority("ROLE_" + appUser.role())));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
