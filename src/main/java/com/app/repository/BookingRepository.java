package com.app.repository;

import com.app.entity.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {


    Flux<Booking> findByFlightId(String flightId);
    Flux<Booking> findByEmail(String email);

}
