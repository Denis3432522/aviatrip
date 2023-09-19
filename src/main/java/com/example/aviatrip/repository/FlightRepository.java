package com.example.aviatrip.repository;

import com.example.aviatrip.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByCompanyId(Long companyId);
}
