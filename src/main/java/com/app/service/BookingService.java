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
import reactor.core.publisher.Mono;

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
        return flightRepository.findById(flightId)
                .switchIfEmpty(Mono.error(new RuntimeException("Flight not found")))
                .flatMap(flight -> processBooking(flight, finalFlightId, request));
    }

    private Mono<BookingResponse> processBooking(Flight flight, String flightId, BookingRequest request) {

        int seatCount = request.getPassengers().size();

        if (flight.getAvailableSeats() < seatCount) {
            return Mono.error(new RuntimeException("Not enough seats available"));
        }

        int newAvailableSeats = flight.getAvailableSeats() - seatCount;
        flight.setAvailableSeats(newAvailableSeats);
        Mono<Flight> savedFlightMono = flightRepository.save(flight); //saved updated flight

        return savedFlightMono.flatMap(savedFlight -> {
            return createAndSaveBooking(flightId, seatCount, flight, request, savedFlight);
        });
    }

    private Mono<BookingResponse> createAndSaveBooking(String flightId, int seatCount,
                                                       Flight flight, BookingRequest request,
                                                       Flight savedFlight) {

        Booking booking = new Booking();
        booking.setPnr(generatePnr());
        booking.setFlightId(flightId);
        booking.setSeatsBooked(seatCount);

        Mono<Booking> savedBookingMono = bookingRepository.save(booking);

        return savedBookingMono.map(savedBooking -> {
            List<Passenger> passengerList = buildPassengerList(request);
            BookingResponse response = toResponse(savedBooking, passengerList, savedFlight);
            return response;
        });
    }

    private List<Passenger> buildPassengerList(BookingRequest request) {
        List<Passenger> passengers = new ArrayList<>();

        for (PassengerRequest p : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setSeatNumber(p.getSeatNumber());
            passengers.add(passenger);
        }

        return passengers;
    }

    private BookingResponse toResponse(Booking booking, List<Passenger> passengerList, Flight flight) {
        BookingResponse response = new BookingResponse();
        response.setBookingId(booking.getId());
        response.setPnr(booking.getPnr());
        response.setFlightId(flight.getFlightNumber());
        response.setSeatsBooked(booking.getSeatsBooked());
        response.setBookingStatus(booking.getStatus().name());

        List<PassengerResponse> passengerResponses = new ArrayList<>();
        for (Passenger p : passengerList) {
            PassengerResponse pr = new PassengerResponse();
            pr.setName(p.getName());
            pr.setSeatNumber(p.getSeatNumber());
            passengerResponses.add(pr);
        }
        response.setPassengers(passengerResponses);

        return response;
    }

    private String generatePnr() {
        return "PNR" + System.currentTimeMillis();
    }
}