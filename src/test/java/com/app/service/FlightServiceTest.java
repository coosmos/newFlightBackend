package com.app.service;

import com.app.dto.FlightSearchRequest;
import com.app.entity.Airline;
import com.app.entity.Flight;
import com.app.repository.AirlineRepository;
import com.app.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private FlightService flightService;

    @Test
    void testAddFlight_success() {

        Flight flight = new Flight();
        flight.setFlightNumber("AI101");

        Airline airline = new Airline();
        airline.setId("AL001");

        when(airlineRepository.findByAirlineCode("AI"))
                .thenReturn(Mono.just(airline));

        Flight savedFlight = new Flight();
        savedFlight.setAirlineId("AL001");

        when(flightRepository.save(flight))
                .thenReturn(Mono.just(savedFlight));

        StepVerifier.create(flightService.addFlight(flight, "AI"))
                .expectNext(savedFlight)
                .verifyComplete();

        verify(airlineRepository, times(1)).findByAirlineCgitode("AI");
        verify(flightRepository, times(1)).save(flight);
    }

    @Test
    void testAddFlight_airlineNotFound() {

        Flight flight = new Flight();

        when(airlineRepository.findByAirlineCode("XX"))
                .thenReturn(Mono.empty());

        StepVerifier.create(flightService.addFlight(flight, "XX"))
                .expectErrorMatches(ex -> ex.getMessage().contains("Airline not found"))
                .verify();

        verify(airlineRepository, times(1)).findByAirlineCode("XX");
        verify(flightRepository, never()).save(any());
    }

    @Test
    void testSearchFlights_success() {

        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromLocation("DEL");
        req.setToLocation("BOM");
        req.setNumberOfPassengers(2);
        req.setTravelDate(LocalDate.of(2025, 1, 10));

        LocalDateTime start = req.getTravelDate().atStartOfDay();
        LocalDateTime end   = req.getTravelDate().plusDays(1).atStartOfDay().minusSeconds(1);

        Flight f1 = new Flight();
        Flight f2 = new Flight();

        when(flightRepository
                .findByFromLocationAndToLocationAndDepartureTimeBetweenAndAvailableSeatsGreaterThanEqual(
                        "DEL", "BOM", start, end, 2))
                .thenReturn(Flux.just(f1, f2));

        StepVerifier.create(flightService.searchFlights(req))
                .expectNext(f1)
                .expectNext(f2)
                .verifyComplete();

        verify(flightRepository, times(1))
                .findByFromLocationAndToLocationAndDepartureTimeBetweenAndAvailableSeatsGreaterThanEqual(
                        "DEL", "BOM", start, end, 2);
    }

    @Test
    void testGetAllFlights_success() {

        Flight f1 = new Flight();
        Flight f2 = new Flight();

        when(flightRepository.findAll())
                .thenReturn(Flux.just(f1, f2));

        StepVerifier.create(flightService.getAllFlights())
                .expectNext(f1)
                .expectNext(f2)
                .verifyComplete();

        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetAllFlights_notFound() {

        when(flightRepository.findAll())
                .thenReturn(Flux.empty());

        StepVerifier.create(flightService.getAllFlights())
                .expectErrorMatches(e -> e.getMessage().contains("Flights not found"))
                .verify();

        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetFlightsByAirline_success() {

        Flight f1 = new Flight();
        Flight f2 = new Flight();

        when(flightRepository.findByAirlineCode("AI"))
                .thenReturn(Flux.just(f1, f2));

        StepVerifier.create(flightService.getFlightsByAirline("AI"))
                .expectNext(f1)
                .expectNext(f2)
                .verifyComplete();

        verify(flightRepository, times(1)).findByAirlineCode("AI");
    }

    @Test
    void testGetFlightsByAirline_notFound() {

        when(flightRepository.findByAirlineCode("XX"))
                .thenReturn(Flux.empty());

        StepVerifier.create(flightService.getFlightsByAirline("XX"))
                .expectErrorMatches(e -> e.getMessage().contains("No flights found"))
                .verify();

        verify(flightRepository, times(1)).findByAirlineCode("XX");
    }
}
