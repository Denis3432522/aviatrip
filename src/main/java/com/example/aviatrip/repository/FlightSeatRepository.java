package com.example.aviatrip.repository;

import com.example.aviatrip.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {

    List<FlightSeat> findByFlightId(Long flightId);
}
