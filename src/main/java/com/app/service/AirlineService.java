package com.app.service;

import com.app.dto.AirlineRequest;
import com.app.entity.Airline;
import com.app.repository.AirlineRepository;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class AirlineService {

    private final AirlineRepository airlineRepository;

    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Mono<String> addAirline(AirlineRequest request) {

        Airline airline = new Airline();
        airline.setAirlineName(request.getAirlineName());
        airline.setAirlineCode(request.getAirlineCode());
        airline.setContactNumber(request.getContactNumber());

        return airlineRepository.save(airline)
                .map(saved -> saved.getId());
    }

    public Flux<Airline> getAllAirlines() {
        return airlineRepository.findAll();
    }
}
