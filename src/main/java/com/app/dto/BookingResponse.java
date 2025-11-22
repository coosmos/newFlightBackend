package com.app.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingResponse {

    private String pnr;
    private String flightId;         // mongo id stored in booking
    private String flightNumber;
    private String bookingId;
    private int seatsBooked;
    private String bookingStatus;
    private String email;
    private LocalDateTime bookingTime;
    private List<PassengerResponse> passengers;
}
