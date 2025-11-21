package com.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "flights")
public class Flight {

    @Id
    private String id;

    private String airlineId; // reference to Airline document
    private String airlineCode;
    private String flightNumber;
    private String fromLocation;
    private String toLocation;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private Integer totalSeats;
    private Integer availableSeats;
    private BigDecimal basePrice;

    private Boolean isActive = true;
}
