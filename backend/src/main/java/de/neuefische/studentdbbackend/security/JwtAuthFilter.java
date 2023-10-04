package de.neuefische.studentdbbackend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);

        if(token != null && !token.isBlank()) {
            try {
                Claims claims = jwtUtils.parseClaims(token);
                setSecurityContext(claims.getSubject());
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token!");
            }

        }
    }

    private void setSecurityContext(String subject) {
        UserDetails appUser = userDetailsService.loadUserByUsername(subject);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                appUser.getUsername(),
                "",
                appUser.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            return authHeader.replace("Bearer", "").trim();
        }

        return null;
    }
}
