package com.example.aviatrip.repository.flight;

import com.example.aviatrip.model.entity.FlightSeat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.util.List;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long>, JpaSpecificationExecutor<FlightSeat> {

    List<FlightSeat> findByFlightId(Long flightId);

    @EntityGraph(attributePaths = {"flight", "flight.company"})
    Page<FlightSeat> findAll( Specification<FlightSeat> spec,  Pageable pageable);

}
