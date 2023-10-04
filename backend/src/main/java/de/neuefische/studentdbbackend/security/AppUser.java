package de.neuefische.studentdbbackend.security;

import org.springframework.data.mongodb.core.index.Indexed;

public record AppUser(
        String id,
        String username,
        String password,
        @Indexed(unique = true)
        String email,
        UserRoles role
) {
}
