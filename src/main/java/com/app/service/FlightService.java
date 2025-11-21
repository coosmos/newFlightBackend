package com.app.service;

import com.app.dto.FlightRequest;
import com.app.dto.FlightSearchRequest;
import com.app.entity.Flight;
import com.app.repository.AirlineRepository;
import com.app.repository.FlightRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;

    public FlightService(FlightRepository flightRepository,
                         AirlineRepository airlineRepository) {
        this.flightRepository = flightRepository;
        this.airlineRepository = airlineRepository;
    }

    public Mono<String> addFlight(Flight flight, String airlineCode) {

        return airlineRepository.findByAirlineCode(airlineCode)
                .switchIfEmpty(Mono.error(new RuntimeException("Airline not found")))
                .flatMap(airline -> {
                    flight.setAirlineId(airline.getId());
                    return flightRepository.save(flight)
                            .map(saved -> saved.getAirlineId());
                });
    }


    public Flux<Flight> searchFlights(FlightSearchRequest request) {

        LocalDate date = request.getTravelDate();
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay().minusSeconds(1);

        return flightRepository
                .findByFromLocationAndToLocationAndDepartureTimeBetweenAndAvailableSeatsGreaterThanEqual(
                        request.getFromLocation(),
                        request.getToLocation(),
                        start,
                        end,
                        request.getNumberOfPassengers()
                );
    }

    public Flux<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flux<Flight> getFlightsByAirline(String airlineCode) {
        return flightRepository.findByAirlineCode(airlineCode);
    }




}
