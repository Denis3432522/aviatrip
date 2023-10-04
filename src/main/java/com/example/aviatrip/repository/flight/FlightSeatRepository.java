package com.example.aviatrip.repository.flight;

import com.example.aviatrip.model.entity.FlightSeat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {

    List<FlightSeat> findByFlightId(Long flightId, Pageable pageable);
}
