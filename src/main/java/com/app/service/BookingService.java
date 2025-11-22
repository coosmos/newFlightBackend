package com.app.service;

import com.app.dto.BookingRequest;
import com.app.dto.BookingResponse;
import com.app.dto.PassengerRequest;
import com.app.dto.PassengerResponse;
import com.app.entity.Booking;
import com.app.entity.Flight;
import com.app.entity.Passenger;
import com.app.repository.BookingRepository;
import com.app.repository.FlightRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository,
                          FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    public Mono<BookingResponse> createBooking(String flightId, BookingRequest request) {
        flightId = flightId.trim();
        String finalFlightId = flightId;

        return flightRepository.findById(finalFlightId)
                .switchIfEmpty(Mono.error(new RuntimeException("Flight not found")))
                .flatMap(flight -> processBooking(flight, finalFlightId, request));
    }

    private Mono<BookingResponse> processBooking(Flight flight, String flightId, BookingRequest request) {
        int seatCount = request.getPassengers().size();
        int newAvailableSeats = flight.getAvailableSeats() - seatCount;
        flight.setAvailableSeats(newAvailableSeats);

        return flightRepository.save(flight)
                .flatMap(savedFlight -> createAndSaveBooking(flightId, seatCount, savedFlight, request));
    }

    private Mono<BookingResponse> createAndSaveBooking(String flightId, int seatCount,
                                                       Flight savedFlight, BookingRequest request) {

        Booking booking = new Booking();
        booking.setPnr(generatePnr());
        booking.setFlightId(flightId);
        booking.setSeatsBooked(seatCount);
        booking.setEmail(request.getEmail());
        booking.setBookingTime(LocalDateTime.now());
        booking.setStatus(Booking.BookingStatus.CONFIRMED);

        // build passenger entities with full info (email, phone)
        List<Passenger> passengerList = buildPassengerListFull(request);
        booking.setPassengers(passengerList);

        // save booking with passengers embedded
        return bookingRepository.save(booking)
                .flatMap(savedBooking -> mapBookingToResponse(savedBooking));
    }

    private List<Passenger> buildPassengerListFull(BookingRequest request) {
        List<Passenger> passengers = new ArrayList<>();
        for (PassengerRequest p : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setSeatNumber(p.getSeatNumber());
            passenger.setEmail(p.getEmail());
            passenger.setPhone(p.getPhone());
            passengers.add(passenger);
        }
        return passengers;
    }

    private BookingResponse buildResponseFrom(Booking booking, Flight flight) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setPnr(booking.getPnr());
        response.setFlightId(booking.getFlightId());
        response.setFlightNumber(flight != null ? flight.getFlightNumber() : null);
        response.setSeatsBooked(booking.getSeatsBooked());
        response.setBookingStatus(booking.getStatus() == null ? null : booking.getStatus().name());
        response.setEmail(booking.getEmail());
        response.setBookingTime(booking.getBookingTime());

        List<PassengerResponse> passengerResponses = new ArrayList<>();
        if (booking.getPassengers() != null) {
            for (Passenger p : booking.getPassengers()) {
                PassengerResponse pr = new PassengerResponse();
                pr.setName(p.getName());
                pr.setSeatNumber(p.getSeatNumber());
                passengerResponses.add(pr);
            }
        }
        response.setPassengers(passengerResponses);
        return response;
    }

    private String generatePnr() {
        return "PNR" + System.currentTimeMillis();
    }

    public Mono<BookingResponse> getBookingByPnr(String pnr) {
        return bookingRepository.findByPnr(pnr)
                .switchIfEmpty(Mono.error(new RuntimeException("Booking not found")))
                .flatMap(this::mapBookingToResponse);
    }


    private Mono<BookingResponse> mapBookingToResponse(Booking booking) {
        if (booking == null) return Mono.empty();
        String flightId = booking.getFlightId();
        if (flightId == null) {

            return Mono.just(buildResponseFrom(booking, null));
        }

        return flightRepository.findById(flightId)
                .switchIfEmpty(Mono.error(new RuntimeException("Flight not found")))
                .map(flight -> buildResponseFrom(booking, flight));
    }

    public Flux<BookingResponse> getBookingHistory(String email) {

        return bookingRepository.findByEmailOrderByCreatedAtDesc(email)
                .switchIfEmpty(Flux.empty())
                .flatMap(this::mapBookingToResponse);
    }

}
