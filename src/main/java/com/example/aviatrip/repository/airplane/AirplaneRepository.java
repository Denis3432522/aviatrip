package com.example.aviatrip.repository.airplane;

import com.example.aviatrip.model.entity.Airplane;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, Long> {

    boolean existsByModel(String model);

    @EntityGraph(attributePaths = "sections")
    List<Airplane> findByCompanyId(long companyId, Pageable pageable);
    @EntityGraph(attributePaths = "sections")
    Optional<Airplane> findAirplaneByModelAndCompanyId(String model, long companyId);

    @EntityGraph(attributePaths = "sections")
    Optional<Airplane> findAirplaneByIdAndCompanyId(long airplaneId, long companyId);
}
