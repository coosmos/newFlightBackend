package com.app.repository;

import com.app.entity.Flight;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

public interface FlightRepository extends ReactiveMongoRepository<Flight, String> {

    Flux<Flight> findByFromLocationAndToLocation(String fromLocation, String toLocation);

    Flux<Flight> findByFromLocationAndToLocationAndDepartureTimeBetweenAndAvailableSeatsGreaterThanEqual(
            String fromLocation,
            String toLocation,
            LocalDateTime start,
            LocalDateTime end,
            Integer seats
    );


    Flux<Flight> findByAirlineCode(String airlineCode);
}
