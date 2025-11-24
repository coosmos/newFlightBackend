package com.app.controller;

import com.app.dto.BookingRequest;
import com.app.dto.BookingResponse;
import com.app.dto.PassengerRequest;
import com.app.service.BookingService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = BookingController.class)
class BookingControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BookingService bookingService;

    @Test
    void testCreateBooking_success() {

        PassengerRequest p = new PassengerRequest();
        p.setName("Ashutosh");
        p.setEmail("ashu@example.com");
        p.setPhone("9999999999");
        p.setSeatNumber("12A");

        BookingRequest request = new BookingRequest();
        request.setContactName("Ashu");
        request.setEmail("ashu@example.com");
        request.setPassengers(List.of(p));

        BookingResponse resp = new BookingResponse();
        resp.setPnr("PNR123");
        resp.setFlightId("FL001");
        resp.setSeatsBooked(1);

        Mockito.when(bookingService.createBooking(anyString(), any()))
                .thenReturn(Mono.just(resp));

        webTestClient.post()
                .uri("/api/v1.0/flight/booking/FL001")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookingResponse.class)
                .value(br -> {
                    assert br.getPnr().equals("PNR123");
                    assert br.getFlightId().equals("FL001");
                });
    }

    @Test
    void testGetBookingByPnr_success() {

        BookingResponse resp = new BookingResponse();
        resp.setPnr("PNR500");
        resp.setFlightId("FL100");

        Mockito.when(bookingService.getBookingByPnr("PNR500"))
                .thenReturn(Mono.just(resp));

        webTestClient.get()
                .uri("/api/v1.0/flight/ticket/PNR500")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookingResponse.class)
                .value(br -> {
                    assert br.getPnr().equals("PNR500");
                    assert br.getFlightId().equals("FL100");
                });
    }

    @Test
    void testGetBookingHistory_success() {

        BookingResponse r1 = new BookingResponse();
        r1.setPnr("PNR1");

        BookingResponse r2 = new BookingResponse();
        r2.setPnr("PNR2");

        Mockito.when(bookingService.getBookingHistory("ashu@example.com"))
                .thenReturn(Flux.just(r1, r2));

        webTestClient.get()
                .uri("/api/v1.0/flight/booking/history/ashu@example.com")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookingResponse.class)
                .hasSize(2);
    }

    @Test
    void testCancelBooking_success() {

        Mockito.when(bookingService.cancelBookingStatus("PNR123"))
                .thenReturn(Mono.just(ResponseEntity.ok("Booking cancelled successfully")));

        webTestClient.put()
                .uri("/api/v1.0/flight/booking/cancel/PNR123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo("Booking cancelled successfully");
    }
}
