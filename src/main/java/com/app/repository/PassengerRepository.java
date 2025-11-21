package com.app.repository;

import com.app.entity.Passenger;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PassengerRepository extends ReactiveMongoRepository<Passenger, String> {
}
