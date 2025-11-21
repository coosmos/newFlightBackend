package com.app.dto;

import com.app.entity.Passenger;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {

    @NotBlank(message = "Contact name is required")
    @Size(min = 2, max = 100, message = "Contact name must be between 2 and 100 characters")
    private String contactName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotNull(message = "Passengers list is required")
    @Size(min = 1, max = 9, message = "Booking must have between 1 and 9 passengers")
    @Valid
    private List<PassengerRequest> passengers;
}