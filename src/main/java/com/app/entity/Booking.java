package com.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;

    private String flightId;
    private String passengerId;

    private Integer seatsBooked;
    private BigDecimal totalPrice;

    private LocalDateTime bookingTime = LocalDateTime.now();

    private String status = "CONFIRMED"; // or CANCELLED
}
