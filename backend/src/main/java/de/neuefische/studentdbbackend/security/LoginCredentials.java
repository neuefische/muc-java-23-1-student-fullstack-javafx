package de.neuefische.studentdbbackend.security;

public record LoginCredentials(
        String username,
        String password
) {
}
