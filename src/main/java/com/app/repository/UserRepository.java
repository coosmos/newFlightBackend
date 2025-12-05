package com.app.repository;


import com.app.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    // find user by email (returns mono because it's reactive)
    Mono<User> findByEmail(String email);

    Mono<Boolean> existsByEmail(String email);
}