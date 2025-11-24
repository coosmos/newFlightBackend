package com.app.service;

import com.app.entity.Passenger;
import com.app.repository.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void testAddPassenger_success() {

        Passenger p = new Passenger();
        p.setName("Ashu");

        when(passengerRepository.save(p))
                .thenReturn(Mono.just(p));

        StepVerifier.create(passengerService.addPassenger(p))
                .expectNext("added passengerAshu")
                .verifyComplete();

        verify(passengerRepository, times(1)).save(p);
    }
}
