package com.app.controller;

import com.app.dto.AirlineRequest;
import com.app.dto.AirlineResponse;
import com.app.service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/flight")
public class AirlineController {
    @Autowired
    private final AirlineService airlineService;
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }




    @PostMapping("airline/register")
    public ResponseEntity<AirlineResponse> registerAirline(@Valid @RequestBody AirlineRequest request){
        // making a response dto here because  we will need to return a response

      AirlineResponse response=  airlineService.addAirline(request); // service would create the entity and save to db

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
