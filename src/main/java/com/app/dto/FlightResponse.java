package com.app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FlightResponse {

    private Long id;
    private String flightNumber;

    private String fromLocation;
    private String toLocation;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer availableSeats;
}
