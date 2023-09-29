package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.config.exception.ResourceNotFoundException;
import com.example.aviatrip.enumeration.FlightSeatClass;
import com.example.aviatrip.model.entity.*;
import com.example.aviatrip.model.request.FlightModel;
import com.example.aviatrip.model.request.FlightPassengerSectionPriceModel;
import com.example.aviatrip.repository.CompanyRepository;
import com.example.aviatrip.repository.flight.FlightRepository;
import com.example.aviatrip.repository.flight.FlightSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;

    public FlightService(FlightRepository flightRepository, FlightSeatRepository flightSeatRepository) {
        this.flightRepository = flightRepository;
        this.flightSeatRepository = flightSeatRepository;
    }

    public void assertCompanyOwnAirplane(Airplane airplane, Long companyId) {
        if(airplane.getCompany().getId() != companyId)
            throw new BadRequestException("you don't own the airplane " + airplane.getModel());
    }

    public void validateFlightModel(FlightModel flightModel, Airplane airplane) {
        Duration duration = Duration.between(flightModel.getTakeoffTimestamp(), flightModel.getLandingTimestamp());
        if(duration.toMinutes() < 30)
            throw new BadRequestException("flight duration must be longer than 30 minutes");

        if(flightModel.getSource().equals(flightModel.getDestination()))
            throw new BadRequestException("source city must not be equal to a destination city");

        Set<String> seatClasses = flightModel.getSections().keySet();
        List<FlightSeatClass> realSeatClasses = airplane.getSections().stream().map(AirplanePassengerSection::getSeatClass).toList();

        if(seatClasses.size() < realSeatClasses.size())
            throw new BadRequestException(("not every airplane seat class specified"));

        for(var seatClass : seatClasses) {
            if(!realSeatClasses.contains(FlightSeatClass.valueOf(seatClass.toUpperCase())))
                throw new BadRequestException("airplane " + airplane.getModel() + " doesn't have a " + seatClass + " seat class");
        }
    }

    @Transactional
    public void createFlight(FlightModel model, Airplane airplane) {

        Flight flight = new Flight(
                airplane,
                model.getTakeoffTimestamp(),
                model.getLandingTimestamp(),
                model.getSource(),
                model.getDestination(),
                airplane.getCompany()
        );

        Flight persistedFlight = flightRepository.save(flight);
        createFlightSeats(model.getSections(), persistedFlight);
    }

    public void createFlightSeats(Map<String, FlightPassengerSectionPriceModel> sections, Flight flight) {
        List<FlightSeat> seats = new ArrayList<>(flight.getAirplane().getCapacity());

        Map<FlightSeatClass, AirplanePassengerSection> airplaneSections = new HashMap<>();

        flight.getAirplane().getSections().forEach(s -> airplaneSections.put(s.getSeatClass(), s));

        for(var section : sections.entrySet()) {
            FlightSeatClass seatClass = FlightSeatClass.valueOf(section.getKey().toUpperCase());

            fillListWithFlightSeatSection(seats, flight, seatClass, section.getValue(), airplaneSections.get(seatClass));
        }

        flightSeatRepository.saveAll(seats);
    }

    private void fillListWithFlightSeatSection(List<FlightSeat> seats, Flight flight,
                                               FlightSeatClass seatClass, FlightPassengerSectionPriceModel section, AirplanePassengerSection airplaneSection) {

        int rowCount = airplaneSection.getSeatCount() / airplaneSection.getRowSeatCount();

        for(int seatRow=1; seatRow <= rowCount; seatRow++) {
            for(int rowSeatPosition=1; rowSeatPosition <= airplaneSection.getRowSeatCount(); rowSeatPosition++) {
                boolean isWindowSeat = (rowSeatPosition == 1) || (rowSeatPosition == airplaneSection.getRowSeatCount());
                int price = isWindowSeat ? section.getWindowSeatPrice() : section.getSeatPrice();
                char seatRowPositionLetter = (char) ('A' + rowSeatPosition - 1);
                String seatPosition = seatRowPositionLetter + "" + seatRow;

                seats.add(new FlightSeat(seatPosition, isWindowSeat, price, seatClass, flight));
            }
        }
    }

    public List<Flight> getCompanyFlights(long companyId) {
        return flightRepository.findByCompanyId(companyId);
    }

    public Flight getCompanyFlight(long flightId, long companyId) {
        Optional<Flight> flight = flightRepository.findByIdAndCompanyId(flightId, companyId);
        if(flight.isPresent())
            return flight.get();

        throw new ResourceNotFoundException("flight with id " + flightId, true);
    }

    public List<FlightSeat> getCompanyFlightSeats(long flightId, long companyId) {

        if(!flightRepository.existsByIdAndCompanyId(flightId, companyId))
            throw new ResourceNotFoundException("flight with id " + flightId, true);

        return flightSeatRepository.findByFlightId(flightId);
    }
}
