package com.app.dto;

import com.app.entity.Booking;
import lombok.Data;

import java.util.List;

@Data
public class BookingResponse {

    private String pnr;
    private String flightId;
    private String bookingId;
    private int seatsBooked;
    private String bookingStatus;
    private List<PassengerResponse> passengers;

}