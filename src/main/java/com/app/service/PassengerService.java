package com.app.service;

import com.app.entity.Passenger;
import com.app.repository.PassengerRepository;
import reactor.core.publisher.Mono;

public class PassengerService {

    private final PassengerRepository passengerRepository;

    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }
    public Mono<String> addPassenger(Passenger passenger) {
       return passengerRepository.save(passenger)
               .map(passenger1 ->"added passenger" + passenger1.getName());
    }
}
