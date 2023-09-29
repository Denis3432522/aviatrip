package com.example.aviatrip.repository.flight;

import com.example.aviatrip.model.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long>, JpaSpecificationExecutor<Flight> {

    List<Flight> findByCompanyId(long companyId);

    boolean existsByIdAndCompanyId(long id, long companyId);

    Optional<Flight> findByIdAndCompanyId(long id, long companyId);
}
