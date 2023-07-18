package de.neuefische.studentdbbackend.security;

public record NewAppUser(
        String username,
        String password,
        String email
) {
}
