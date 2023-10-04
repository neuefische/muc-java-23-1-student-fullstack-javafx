package de.neuefische.studentdbbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;


    public String login(LoginCredentials credentials) {
        System.out.println("LOGIN in service");

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.username(),
                    credentials.password()
            ));
            return jwtUtils.createToken(new HashMap<>(), credentials.username());
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials");
        }
    }

    public String register(NewAppUser newAppUser){
        System.out.println("LOGIN  in service");


        var user = new AppUser(
                "1",
                newAppUser.username(),
                newAppUser.password(),
                newAppUser.email(),
                UserRoles.USER
        );

        appUserRepository.save(user);
        return jwtUtils.createToken(new HashMap<>(), user.username());
    }


}
