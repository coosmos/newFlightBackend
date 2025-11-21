package com.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FlightSearchRequest {

    @NotBlank(message = "From location is required")
    private String fromLocation;

    @NotBlank(message = "To location is required")
    private String toLocation;

    @NotNull(message = "Travel date is required")
    @FutureOrPresent(message = "Travel date cannot be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate travelDate;

    @NotNull(message = "Number of passengers is required")
    @Min(value = 1, message = "At least 1 passenger is required")
    @Max(value = 9, message = "Maximum 9 passengers allowed")
    private Integer numberOfPassengers;

    private Boolean isRoundTrip = false;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
}
