package com.example.aviatrip.repository.airplane;

import com.example.aviatrip.model.entity.AirplanePassengerSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirplanePassengerSectionRepository extends JpaRepository<AirplanePassengerSection, Long> {
}
