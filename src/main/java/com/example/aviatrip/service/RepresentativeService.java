package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.config.exception.ValueNotUniqueException;
import com.example.aviatrip.config.requestmodel.FlightModel;
import com.example.aviatrip.config.requestmodel.FlightSeatSectionModel;
import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.enumeration.FlightSeatClass;
import com.example.aviatrip.model.*;
import com.example.aviatrip.repository.CompanyRepository;
import com.example.aviatrip.repository.FlightRepository;
import com.example.aviatrip.repository.RepresentativeRepository;
import com.example.aviatrip.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final CompanyRepository companyRepository;
    private final FlightRepository flightRepository;
    private final SeatRepository seatRepository;

    public RepresentativeService(RepresentativeRepository representativeRepository, CompanyRepository companyRepository, FlightRepository flightRepository, SeatRepository seatRepository) {
        this.representativeRepository = representativeRepository;
        this.companyRepository = companyRepository;
        this.flightRepository = flightRepository;
        this.seatRepository = seatRepository;
    }

    public void assertCompanyNameUniqueness(String companyName) {
        if(companyRepository.existsByName(companyName))
            throw new ValueNotUniqueException("company name", true);
    }

    public void createRepresentativeAndCompany(User model, String companyName) {
        AviaCompanyRepresentative representative = representativeRepository.save(new AviaCompanyRepresentative(model));
        AviaCompany aviaCompany = new AviaCompany(companyName, representative);
        companyRepository.save(aviaCompany);
    }
    
    public void validateFlightModel(FlightModel flightModel) {
        Duration duration = Duration.between(flightModel.getTakeoffTimestamp(), flightModel.getLandingTimestamp());
        if(duration.toMinutes() < 30)
            throw new BadRequestException("flight duration must be longer than 30 minutes");

        if(flightModel.getSource().equals(flightModel.getDestination()))
            throw new BadRequestException("source city must not be equal to a destination city");

        var seatSections = flightModel.getSeats().values();

        int totalSeatCount = seatSections.stream()
                .map(FlightSeatSectionModel::getCount)
                .reduce(0, Integer::sum);

        if(totalSeatCount > 500)
            throw new BadRequestException("total seat count must be less than 500 seats unless you got a spaceship");
    }

    public Flight createFlight(FlightModel model, Long companyId) {
        AviaCompany company = companyRepository.findById(companyId).orElseThrow(RuntimeException::new);

        Flight flight = new Flight(
                model.getAirplaneModel(),
                model.getTakeoffTimestamp(),
                model.getLandingTimestamp(),
                City.valueOf(model.getSource().toUpperCase()),
                City.valueOf(model.getDestination().toUpperCase()),
                company
        );

        return flightRepository.save(flight);
    }
    
    public void createFlightSeats(Map<String, FlightSeatSectionModel> sections, Flight flight) {
        List<FlightSeat> seats = new ArrayList<>(100);

        for(var section : sections.entrySet()) {
            fillListWithFlightSeats(seats, flight, section);
        }

        seatRepository.saveAll(seats);
    }

    private void fillListWithFlightSeats(List<FlightSeat> seats, Flight flight, Map.Entry<String, FlightSeatSectionModel> section) {
        var seatClass = FlightSeatClass.valueOf(section.getKey().toUpperCase());
        int price = section.getValue().getPrice();
        int count = section.getValue().getCount();

        for(int i=1; i<=count; i++) {
            seats.add(new FlightSeat(i, price, seatClass, flight));
        }
    }
}