package com.app.service;

import com.app.dto.BookingRequest;
import com.app.dto.PassengerRequest;
import com.app.entity.Booking;
import com.app.entity.Flight;
import com.app.repository.BookingRepository;
import com.app.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private BookingService bookingService;

    private Flight dummyFlight() {
        Flight f = new Flight();
        f.setId("FL001");
        f.setAvailableSeats(10);
        f.setTotalSeats(10);
        f.setFlightNumber("AI202");
        return f;
    }

    private BookingRequest dummyBookingRequest() {
        BookingRequest req = new BookingRequest();
        req.setContactName("Ashutosh");
        req.setEmail("test@test.com");

        PassengerRequest p = new PassengerRequest();
        p.setName("John");
        p.setEmail("john@mail.com");
        p.setPhone("9999999999");
        p.setSeatNumber("1A");

        req.setPassengers(List.of(p));
        return req;
    }

    private Booking dummyBooking() {
        Booking b = new Booking();
        b.setId("B001");
        b.setPnr("PNRTEST");
        b.setFlightId("FL001");
        b.setSeatsBooked(1);
        b.setEmail("test@test.com");
        b.setBookingTime(LocalDateTime.now());
        b.setStatus(Booking.BookingStatus.CONFIRMED);
        return b;
    }

    @Test
    void testCreateBooking_success() {

        Flight flight = dummyFlight();
        BookingRequest request = dummyBookingRequest();

        Booking savedBooking = dummyBooking();

        when(flightRepository.findById("FL001"))
                .thenReturn(Mono.just(flight));

        when(flightRepository.save(any()))
                .thenReturn(Mono.just(flight));

        when(bookingRepository.save(any()))
                .thenReturn(Mono.just(savedBooking));

        StepVerifier.create(bookingService.createBooking("FL001", request))
                .assertNext(res -> {
                    assert res.getPnr() != null;
                    assert res.getSeatsBooked() == 1;
                    assert res.getEmail().equals("test@test.com");
                })
                .verifyComplete();

        verify(flightRepository, times(2)).findById("FL001");
        verify(bookingRepository, times(1)).save(any());
    }

    @Test
    void testCreateBooking_flightNotFound() {

        BookingRequest request = dummyBookingRequest();

        when(flightRepository.findById("FL999"))
                .thenReturn(Mono.empty());

        StepVerifier.create(bookingService.createBooking("FL999", request))
                .expectErrorMatches(ex -> ex.getMessage().contains("Flight not found"))
                .verify();
    }

    @Test
    void testGetBookingByPnr_success() {

        Booking booking = dummyBooking();
        Flight flight = dummyFlight();

        when(bookingRepository.findByPnr("PNRTEST"))
                .thenReturn(Mono.just(booking));

        when(flightRepository.findById("FL001"))
                .thenReturn(Mono.just(flight));

        StepVerifier.create(bookingService.getBookingByPnr("PNRTEST"))
                .assertNext(res -> {
                    assert res.getPnr().equals("PNRTEST");
                    assert res.getFlightId().equals("FL001");
                })
                .verifyComplete();
    }

    @Test
    void testGetBookingByPnr_notFound() {

        when(bookingRepository.findByPnr("PNRXXX"))
                .thenReturn(Mono.empty());

        StepVerifier.create(bookingService.getBookingByPnr("PNRXXX"))
                .expectErrorMatches(e -> e.getMessage().contains("Booking not found"))
                .verify();
    }

    @Test
    void testGetBookingHistory_success() {

        Booking booking = dummyBooking();
        Flight flight = dummyFlight();

        when(bookingRepository.findByEmailOrderByCreatedAtDesc("test@test.com"))
                .thenReturn(Flux.just(booking));

        when(flightRepository.findById("FL001"))
                .thenReturn(Mono.just(flight));

        StepVerifier.create(bookingService.getBookingHistory("test@test.com"))
                .assertNext(res -> {
                    assert res.getEmail().equals("test@test.com");
                })
                .verifyComplete();
    }

    @Test
    void testGetBookingHistory_empty() {

        when(bookingRepository.findByEmailOrderByCreatedAtDesc("test@test.com"))
                .thenReturn(Flux.empty());

        StepVerifier.create(bookingService.getBookingHistory("test@test.com"))
                .verifyComplete();
    }

    @Test
    void testCancelBookingStatus_success() {

        Booking booking = dummyBooking();

        when(bookingRepository.findByEmail("test@test.com"))
                .thenReturn(Flux.just(booking));

        when(bookingRepository.save(any()))
                .thenReturn(Mono.just(booking));

        StepVerifier.create(bookingService.cancelBookingStatus("test@test.com"))
                .assertNext(res -> {})
                .verifyComplete();

        verify(bookingRepository, times(1)).findByEmail("test@test.com");
        verify(bookingRepository, atLeastOnce()).save(any());
    }

    @Test
    void testCancelBookingStatus_noBookings() {

        when(bookingRepository.findByEmail("xyz@mail.com"))
                .thenReturn(Flux.empty());

        StepVerifier.create(bookingService.cancelBookingStatus("xyz@mail.com"))
                .expectErrorMatches(e -> e.getMessage().contains("No bookings found"))
                .verify();
    }
}
