package com.app.controller;

import com.app.dto.FlightSearchRequest;
import com.app.entity.Flight;
import com.app.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping("/add")
    public Mono<String> addFlight(
            @RequestParam String airlineCode,
            @RequestBody Flight flight) {

        return flightService.addFlight(flight, airlineCode);
    }

    @PostMapping("/search")
    public Flux<Flight> search(@Valid @RequestBody FlightSearchRequest request) {
        return flightService.searchFlights(request);
    }

    @GetMapping
    public Flux<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/airline/{airlineCode}")
    public Flux<Flight> getFlightsByAirline(@PathVariable String airlineCode) {
        return flightService.getFlightsByAirline(airlineCode);
    }


}
