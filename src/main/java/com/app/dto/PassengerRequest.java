package com.app.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PassengerRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "enter valid email")
    private String email;
    @Pattern(regexp = "^[0-9]{10,10}" , message = "enter valid phone number")
    private String phone;
    @NotBlank
    private String seatNumber;
}
