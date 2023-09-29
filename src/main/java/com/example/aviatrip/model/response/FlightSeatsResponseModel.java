package com.example.aviatrip.model.response;

import com.example.aviatrip.model.entity.FlightSeat;

import java.util.List;

public class FlightSeatsResponseModel {

    private final List<FlightSeat> seats;

    public FlightSeatsResponseModel(List<FlightSeat> seats) {
        this.seats = seats;
    }

    public List<FlightSeat> getSeats() {
        return seats;
    }
}
