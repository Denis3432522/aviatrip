package com.example.aviatrip.controller.representative;

import com.example.aviatrip.model.entity.Airplane;
import com.example.aviatrip.model.request.FlightModel;
import com.example.aviatrip.model.response.FlightsResponseModel;
import com.example.aviatrip.model.response.FlightSeatsResponseModel;
import com.example.aviatrip.model.entity.Flight;
import com.example.aviatrip.service.AirplaneService;
import com.example.aviatrip.service.FlightService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/representative/flights")
public class FlightManagementController {

    private final FlightService flightService;
    private final AirplaneService airplaneService;

    public FlightManagementController(FlightService flightService, AirplaneService airplaneService) {
        this.flightService = flightService;
        this.airplaneService = airplaneService;
    }

    @PostMapping
    @Transactional
    public void createFlight(@RequestBody @Valid FlightModel model, @AuthenticationPrincipal Long companyId) {
        Airplane airplane = airplaneService.getAirplane(model.getAirplaneModel());

        flightService.assertCompanyOwnAirplane(airplane, companyId);
        flightService.validateFlightModel(model, airplane);

        flightService.createFlight(model, airplane);
    }

    @GetMapping
    public FlightsResponseModel getCompanyFlights(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @AuthenticationPrincipal Long companyId) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        return new FlightsResponseModel(flightService.getCompanyFlights(companyId, pageRequest));
    }

    @GetMapping("/{flightId}")
    public Flight getCompanyFlight(@PathVariable int flightId, @AuthenticationPrincipal Long companyId) {
        return flightService.getCompanyFlight(flightId, companyId);
    }

    @GetMapping("/{flightId}/seats")
    public FlightSeatsResponseModel getCompanyFlightSeats(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @PathVariable int flightId, @AuthenticationPrincipal Long companyId) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);
        return new FlightSeatsResponseModel(flightService.getCompanyFlightSeats(flightId, companyId, pageRequest));
    }
}



