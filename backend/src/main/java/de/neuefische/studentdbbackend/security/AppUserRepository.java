package de.neuefische.studentdbbackend.security;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {
    Optional<AppUser> findAppUserByUsername(String username);

    Optional<AppUser> findByEmail(String username);
}
