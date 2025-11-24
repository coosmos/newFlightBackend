package com.app.controller;

import com.app.dto.FlightSearchRequest;
import com.app.entity.Flight;
import com.app.service.FlightService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = FlightController.class)
class FlightControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private FlightService flightService;

    @Test
    void testAddFlight_success() {

        Flight flight = new Flight();
        flight.setId("FL001");

        Mockito.when(flightService.addFlight(any(), anyString()))
                .thenReturn(Mono.just(flight));
        // fire fake http post request

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/flights/add")
                        .queryParam("airlineCode", "AI")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(flight)
                .exchange()
                .expectStatus().isOk();  // expecting 201 Created
    }

    @Test
    void testSearchFlights_success() {

        Flight f1 = new Flight();
        f1.setId("FL001");
        f1.setFromLocation("DEL");
        f1.setToLocation("BOM");

        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromLocation("DEL");
        req.setToLocation("BOM");
        req.setTravelDate(LocalDate.now());
        req.setNumberOfPassengers(1);

        Mockito.when(flightService.searchFlights(any()))
                .thenReturn(Flux.just(f1));

        webTestClient.post()
                .uri("/api/flights/search")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Flight.class)
                .hasSize(1)
                .value(list -> {
                    assert list.get(0).getFromLocation().equals("DEL");
                    assert list.get(0).getToLocation().equals("BOM");
                });
    }
    @Test
    void testGetAllFlights_success() {

        Flight f1 = new Flight();
        f1.setId("FL001");

        Flight f2 = new Flight();
        f2.setId("FL002");

        Mockito.when(flightService.getAllFlights())
                .thenReturn(Flux.just(f1, f2));

        webTestClient.get()
                .uri("/api/flights")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Flight.class)
                .hasSize(2);
    }
    @Test
    void testGetFlightsByAirline_success() {

        Flight f = new Flight();
        f.setId("FL100");
        f.setAirlineCode("AI");

        Mockito.when(flightService.getFlightsByAirline("AI"))
                .thenReturn(Flux.just(f));

        webTestClient.get()
                .uri("/api/flights/airline/AI")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Flight.class)
                .hasSize(1)
                .value(list -> {
                    assert list.get(0).getAirlineCode().equals("AI");
                });
    }
}
