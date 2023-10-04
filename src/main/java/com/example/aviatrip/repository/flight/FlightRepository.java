package com.example.aviatrip.repository.flight;

import com.example.aviatrip.model.entity.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByCompanyId(long companyId, Pageable pageable);

    boolean existsByIdAndCompanyId(long id, long companyId);

    Optional<Flight> findByIdAndCompanyId(long id, long companyId);
}
