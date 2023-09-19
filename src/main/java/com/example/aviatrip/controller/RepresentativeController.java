package com.example.aviatrip.controller;

import com.example.aviatrip.config.requestmodel.FlightModel;
import com.example.aviatrip.config.responsemodel.FlightsResponseModel;
import com.example.aviatrip.config.responsemodel.FlightSeatsResponseModel;
import com.example.aviatrip.model.Flight;
import com.example.aviatrip.service.RepresentativeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/representative")
public class RepresentativeController {

    private final RepresentativeService representativeService;

    public RepresentativeController(RepresentativeService representativeService) {
        this.representativeService = representativeService;
    }

    @PostMapping("/flights")
    @Transactional
    public void createFlight(@RequestBody @Valid FlightModel model, @AuthenticationPrincipal Long userId) {
        representativeService.validateFlightModel(model);

        Flight flight = representativeService.createFlight(model, userId);
        representativeService.createFlightSeats(model.getSeats(), flight);
    }

    @GetMapping("/flights")
    public FlightsResponseModel getFlights(@AuthenticationPrincipal Long userId) {
        return new FlightsResponseModel(representativeService.getFlights(userId));
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable int id) {
        return representativeService.getFlight(id);
    }

    @GetMapping("/flights/{flightId}/seats")
    public FlightSeatsResponseModel getFlightSeats(@PathVariable int flightId) {
        return new FlightSeatsResponseModel(representativeService.getFlightSeats(flightId));
    }
}



