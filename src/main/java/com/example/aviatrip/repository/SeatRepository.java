package com.example.aviatrip.repository;

import com.example.aviatrip.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<FlightSeat, Long> {

}
