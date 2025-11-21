package com.app.controller;

import com.app.dto.AirlineRequest;
import com.app.entity.Airline;
import com.app.service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<String>> registerAirline(
            @Valid @RequestBody AirlineRequest request) {

        return airlineService.addAirline(request)
                .map(id -> ResponseEntity.status(HttpStatus.CREATED).body(id));
    }

    @GetMapping
    public Flux<Airline> getAllAirlines() {
        return airlineService.getAllAirlines();
    }
}
