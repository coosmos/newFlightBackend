package com.app.dto;

import jdk.jfr.DataAmount;
import lombok.Data;

@Data
public class AirlineResponse {

    private Long id;
    private String airlineName;
    private String airlineCode;
    private String contactNumber;

}
