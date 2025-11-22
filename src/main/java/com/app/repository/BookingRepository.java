package com.app.repository;

import com.app.dto.BookingResponse;
import com.app.entity.Booking;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BookingRepository extends ReactiveMongoRepository<Booking, String> {

    Flux<Booking> findByFlightId(String flightId);
    Flux<Booking> findByEmail(String email);

    Mono<Booking> findByPnr(String pnr);

    Flux<Booking> findByEmailOrderByCreatedAtDesc(String email);
}
