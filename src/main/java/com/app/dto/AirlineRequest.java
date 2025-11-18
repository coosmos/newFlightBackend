package com.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AirlineRequest {
    @NotBlank(message="Airline name cannot be empty")
    private String airlineName;
    @NotBlank(message="Airline code is required")
    @Size(min=2,max=5 , message="Airline code must be between 2 and 5 characters ")
    private String airlineCode;
    @NotBlank(message="Contact number is required ")
    @Pattern(regexp = "^[0-9]{10}$", message="contact number must be 10 digits ")
    private String contactNumber;

}
