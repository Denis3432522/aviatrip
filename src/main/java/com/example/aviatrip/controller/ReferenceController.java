package com.example.aviatrip.controller;

import com.example.aviatrip.service.ReferenceService;
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
    public List<String> getCities() {
        return referenceService.getAvailableCities();
    }

    @GetMapping("/available-flight-seat-classes")
    public List<String> getFlightSeatClasses() {
        return referenceService.getAvailableFlightSeatClasses();
    }
}
