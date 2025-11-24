package com.app.service;


import com.app.entity.Airline;
import com.app.entity.Flight;
import com.app.repository.AirlineRepository;
import com.app.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {

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
        airline.setAirlineCode("AL101");
        when(airlineRepository.findByAirlineCode("AI"))
                .thenReturn(Mono.just(airline));
        Flight savedFlight=new Flight();
        savedFlight.setAirlineId("AL001");

        when(flightRepository.save(flight))
                .thenReturn(Mono.just(savedFlight));

        StepVerifier.create(flightService.addFlight(flight,"AI"))
                .expectNext("AL001")
                .verifyComplete();
        verify(airlineRepository,times(1)).findByAirlineCode("AI");
        verify(flightRepository,times(1)).save(flight);

    }
}
