package com.example.aviatrip.repository.airplane;

import com.example.aviatrip.model.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    boolean existsByModel(String model);

    Optional<Airplane> findAirplaneByModel(String model);
}
