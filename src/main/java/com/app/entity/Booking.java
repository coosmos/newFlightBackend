package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "bookings")
public class Booking extends BaseEntity {

    @Id
    private String id;
    private String pnr;
    private String flightId;
    private Integer seatsBooked;
    private String email;
    private List<Passenger> passengers;

    private LocalDateTime bookingTime = LocalDateTime.now();

    private BookingStatus status = BookingStatus.CONFIRMED;

    public enum BookingStatus {
        CONFIRMED,  CANCELLED
    }
}
