package com.calcpal.userservice.repository;

import com.calcpal.userservice.collection.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String username);

    Optional<User> findByActivationTokenToken(String token);

    Optional<User> findByAuthenticationTokenAccessToken(String jwt);
}
