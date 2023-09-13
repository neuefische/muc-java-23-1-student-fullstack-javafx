package de.neuefische.studentdbbackend.security;

public record AppUserResponse(
        String id,
        String username,
        String email,
        UserRoles role
) {
}
