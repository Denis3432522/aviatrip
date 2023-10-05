package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.config.exception.ResourceNotFoundException;
import com.example.aviatrip.config.exception.ValueNotUniqueException;
import com.example.aviatrip.model.entity.Airplane;
import com.example.aviatrip.model.entity.AirplanePassengerSection;
import com.example.aviatrip.model.entity.AviaCompany;
import com.example.aviatrip.repository.CompanyRepository;
import com.example.aviatrip.repository.airplane.AirplaneRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final CompanyRepository companyRepository;

    public AirplaneService(AirplaneRepository airplaneRepository, CompanyRepository companyRepository) {
        this.airplaneRepository = airplaneRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public void createAirplane(Airplane airplane, Long companyId) {
        assertAirplaneModelUnique(airplane.getModel());

        var sections = airplane.getSections();
        int totalSeatCount = getTotalSeatCount(sections);

        validatePassengerSections(sections, totalSeatCount);

        for(AirplanePassengerSection section : sections) {
            section.setAirplane(airplane);
        }

        AviaCompany company = companyRepository.findById(companyId).orElseThrow(RuntimeException::new);

        airplane.setCompany(company);
        airplane.setCapacity(totalSeatCount);

        airplaneRepository.save(airplane);
    }

    public void assertAirplaneModelUnique(String model) {
        if(airplaneRepository.existsByModel(model))
            throw new ValueNotUniqueException("model " + model, true);
    }

    public void validatePassengerSections(Set<AirplanePassengerSection> sections, int totalSeatCount) {
        if(totalSeatCount > 500)
            throw new BadRequestException("total seat count must be less than 500 seats unless you got a spaceship");

        long distinctClassCount = sections.stream().map(AirplanePassengerSection::getSeatClass).distinct().count();

        if(sections.size() != distinctClassCount)
            throw new BadRequestException("seat classes in an airplane passenger sections must be different");

        if(sections.stream().anyMatch(s -> s.getSeatCount() % s.getRowSeatCount() != 0))
            throw new BadRequestException("all seat rows must have the same number of seats within one section");
    }

    public int getTotalSeatCount(Set<AirplanePassengerSection> sections) {
        return sections.stream().mapToInt(AirplanePassengerSection::getSeatCount).sum();
    }

    public List<Airplane> getCompanyAirplanes(long companyId, Pageable pageRequest) {
        return airplaneRepository.findByCompanyId(companyId, pageRequest);
    }

    public Airplane getCompanyAirplaneById(long airplaneId, long companyId) {
        Optional<Airplane> airplane = airplaneRepository.findAirplaneByIdAndCompanyId(airplaneId, companyId);

        return airplane.orElseThrow(() -> new ResourceNotFoundException("airplane with id " + airplaneId, true));
    }

    public Airplane getCompanyAirplaneByModel(String airplaneModel, long companyId) {
        Optional<Airplane> airplane = airplaneRepository.findAirplaneByModelAndCompanyId(airplaneModel, companyId);

        return airplane.orElseThrow(() -> new ResourceNotFoundException("airplane " + airplaneModel, true));
    }
}
