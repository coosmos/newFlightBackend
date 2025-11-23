package com.app.service;

import com.app.dto.AirlineRequest;
import com.app.entity.Airline;
import com.app.repository.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AirlineServiceTest {

    @Mock
    private AirlineRepository airlineRepository;

    @InjectMocks
    private AirlineService airlineService;

    @Test
    void testAddAirline_success() {

        AirlineRequest request = new AirlineRequest();
        request.setAirlineName("IndiGo");
        request.setAirlineCode("6E");
        request.setContactNumber("9898989898");

        Airline savedAirline = new Airline();
        savedAirline.setId("A1");
        savedAirline.setAirlineName("IndiGo");
        savedAirline.setAirlineCode("6E");
        savedAirline.setContactNumber("9898989898");

        when(airlineRepository.save(any(Airline.class)))
                .thenReturn(Mono.just(savedAirline));

        StepVerifier.create(airlineService.addAirline(request))
                .expectNext("A1")
                .verifyComplete();

        verify(airlineRepository, times(1)).save(any(Airline.class));
    }

    @Test
    void testGetAllAirlines_success() {

        Airline a1 = new Airline("A1", "6E", "IndiGo", "9898989898", true);
        Airline a2 = new Airline("A2", "AI", "Air India", "9898989899", true);

        when(airlineRepository.findAll())
                .thenReturn(Flux.just(a1, a2));

        StepVerifier.create(airlineService.getAllAirlines())
                .expectNext(a1)
                .expectNext(a2)
                .verifyComplete();

        verify(airlineRepository, times(1)).findAll();
    }

}
