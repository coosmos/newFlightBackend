package com.app.controller;

import com.app.dto.AirlineRequest;
import com.app.entity.Airline;
import com.app.service.AirlineService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AirlineController.class)
class AirlineControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AirlineService airlineService;

    @Test
    void testRegisterAirline_success() {

        AirlineRequest request = new AirlineRequest();
        request.setAirlineName("IndiGo");
        request.setAirlineCode("6E");
        request.setContactNumber("9898989898");

        Mockito.when(airlineService.addAirline(any()))
                .thenReturn(Mono.just("A1"));

        webTestClient.post()
                .uri("/api/airlines/register")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(String.class)
                .isEqualTo("A1");
    }

    @Test
    void testGetAllAirlines_success() {

        Airline a1 = new Airline("A1", "6E", "IndiGo", "9898989898", true);
        Airline a2 = new Airline("A2", "AI", "Air India", "8888888888", true);

        Mockito.when(airlineService.getAllAirlines())
                .thenReturn(Flux.just(a1, a2));

        webTestClient.get()
                .uri("/api/airlines")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Airline.class)
                .hasSize(2)
                .contains(a1, a2);
    }
}
