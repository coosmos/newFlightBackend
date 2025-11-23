package com.app.controller;

import com.app.dto.BookingRequest;
import com.app.dto.BookingResponse;
import com.app.service.BookingService;
import com.app.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1.0")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/booking/{flight_id}")
    public Mono<BookingResponse> createBooking(@PathVariable("flight_id") String flight_id,
                                               @RequestBody BookingRequest bookingRequest) {
       return bookingService.createBooking(flight_id,bookingRequest);

    }
    //adding other endpoints later --TODO




}
