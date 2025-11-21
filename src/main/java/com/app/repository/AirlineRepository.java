package com.app.repository;

import com.app.entity.Airline;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface AirlineRepository extends ReactiveMongoRepository<Airline, String> {

    Mono<Airline> findByAirlineCode(String airlineCode);
}
