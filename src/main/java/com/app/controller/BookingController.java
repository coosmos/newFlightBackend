package com.app.controller;

import com.app.dto.BookingRequest;
import com.app.dto.BookingResponse;
import com.app.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/flight")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/booking/{flight_id}")
    public Mono<BookingResponse> createBooking(
            @PathVariable("flight_id") String flightId,
            @RequestBody BookingRequest bookingRequest) {
        return bookingService.createBooking(flightId, bookingRequest);
    }

    @GetMapping("/ticket/{pnr}")
    public Mono<BookingResponse> getBookingByPnr(@PathVariable String pnr) {
        return bookingService.getBookingByPnr(pnr);
    }

    @GetMapping("/booking/history/{email}")
    public Flux<BookingResponse> getBookingHistory(@PathVariable String email) {
        return bookingService.getBookingHistory(email);
    }

    @PutMapping("/booking/cancel/{pnr}")
    public Mono<ResponseEntity<String>> cancelBooking(@PathVariable String pnr) {
        return bookingService.cancelBookingStatus(pnr);
    }
}
