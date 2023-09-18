package com.example.aviatrip.service;

import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.enumeration.FlightSeatClass;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ReferenceService {

    public List<String> getAvailableCities() {
        return Arrays.stream(City.values()).map(c -> c.name().toLowerCase()).toList();
    }

    public List<String> getAvailableFlightSeatClasses() {
        return Arrays.stream(FlightSeatClass.values()).map(c -> c.name().toLowerCase()).toList();
    }
}
