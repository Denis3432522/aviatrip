package com.example.aviatrip.config.responsemodel;

import com.example.aviatrip.model.Flight;

import java.util.List;

public class FlightsResponseModel {

    private final List<Flight> flights;

    public FlightsResponseModel(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
