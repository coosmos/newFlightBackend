package com.app.controller;

import com.app.dto.BookingRequest;
import com.app.dto.BookingResponse;
import com.app.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Book;

@RestController
@RequestMapping("/api/v1.0/flight")
public class BookingController {

    private final BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking/{flight_id}")
    public Mono<BookingResponse> createBooking(@PathVariable("flight_id") String flight_id,
                                               @RequestBody BookingRequest bookingRequest) {
       return bookingService.createBooking(flight_id,bookingRequest);

    }

    @GetMapping("/ticket/{pnr}")
    public Mono<BookingResponse> getBookingByPnr(@PathVariable String pnr) {

        return bookingService.getBookingByPnr(pnr);
    }

    @GetMapping("/booking/hhistory/{email}")
    public Flux<BookingResponse> getBookingHistory(@PathVariable String email) {
        return  bookingService.getBookingHistory(email);
    }
    @PutMapping("/booking/cancel/{pnr}")
    public Mono<ServerResponse> cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelBookingStatus(pnr);
    }







}
