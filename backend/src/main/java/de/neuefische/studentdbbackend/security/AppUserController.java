package de.neuefische.studentdbbackend.security;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.HashMap;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AuthenticationManager authenticationManager;
    private final AppUserService appUserService;
    private JWTUtils jwtUtils;

    @GetMapping("/me")
    public String getMe(Principal principal){
        if (principal != null) {
            return principal.getName();
        }
        return "anonymousUser";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginCredentials credentials){
        System.out.println("LOGIN");
        return appUserService.login(credentials);
    }

    @PostMapping("/register")
    public String register(@RequestBody NewAppUser newAppUser){
        System.out.println("REGISTER");

        return appUserService.register(newAppUser);
    }

    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "anonymousUser";
    }
}
