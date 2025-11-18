package com.app.service;

import com.app.dto.AirlineRequest;
import com.app.dto.AirlineResponse;
import com.app.entity.Airline;
import com.app.repository.AirlineRepository;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {
    AirlineRepository repo;
    public AirlineService(AirlineRepository repo) {
        this.repo = repo;
    }

    public AirlineResponse addAirline(AirlineRequest request){

        Airline airline=new Airline();
        airline.setAirlineCode(request.getAirlineCode());
        airline.setAirlineName(request.getAirlineName());
        airline.setContactNumber(request.getContactNumber());
       Airline saved= repo.save(airline);

        AirlineResponse response=new AirlineResponse();
        response.setId(saved.getId());
        response.setAirlineCode(request.getAirlineCode());
        response.setAirlineName(request.getAirlineName());
        response.setContactNumber(request.getContactNumber());
        return response;

    }
}
