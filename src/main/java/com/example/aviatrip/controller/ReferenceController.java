package com.example.aviatrip.controller;

import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.service.ReferenceService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reference")
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @GetMapping("/available-cities")
    public Object getCities() {
        return new Object() {
            @JsonProperty("cities")
            private final List<String> cities = referenceService.getAvailableCities();
        };
    }

    @GetMapping("/available-flight-seat-classes")
    public Object getFlightSeatClasses() {
        return new Object() {
            @JsonProperty("flight_seat_classes")
            private final List<String> flightSeatClasses = referenceService.getAvailableFlightSeatClasses();
        };
    }
}
