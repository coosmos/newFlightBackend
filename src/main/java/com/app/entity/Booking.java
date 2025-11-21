package com.app.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "bookings")
public class Booking {

    @Id
    private String id;
    private String pnr;
    private String flightId; //reference to flightTable
    private Integer seatsBooked;
    private String email;
    private List<Passenger> passengers;

    private LocalDateTime bookingTime = LocalDateTime.now();

    private BookingStatus status = BookingStatus.CONFIRMED;

    public enum  BookingStatus {
        CONFIRMED,  CANCELLED
    }
}
