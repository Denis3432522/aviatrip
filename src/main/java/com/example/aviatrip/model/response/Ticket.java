package com.example.aviatrip.model.response;

import com.example.aviatrip.enumeration.City;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;

public class Ticket {

    @JsonProperty("flight_seat_id")
    long flightSeatId;

    @JsonProperty("is_window_seat")
    boolean isWindowSeat;

    @JsonProperty("position")
    String position;

    @JsonProperty("price")
    int price;

    @JsonProperty("flight")
    TicketFlight flight;

    @JsonProperty("company_name")
    String companyName;

    @JsonProperty("airplane_name")
    String airplaneName;

    public Ticket(long flightSeatId, boolean isWindowSeat, String position, int price, long flightId, City destination, ZonedDateTime landingTimestamp, City source, ZonedDateTime takeoffTimestamp, String companyName, String airplaneName) {
        this.flightSeatId = flightSeatId;
        this.isWindowSeat = isWindowSeat;
        this.position = position;
        this.price = price;
        this.flight = new TicketFlight(flightId, destination, landingTimestamp, source, takeoffTimestamp);
        this.companyName = companyName;
        this.airplaneName = airplaneName;
    }

    private static class TicketFlight {

        @JsonProperty("flight_id")
        long flightId;

        @JsonProperty("destination")
        City destination;

        @JsonProperty("landing_timestamp")
        ZonedDateTime landingTimestamp;

        @JsonProperty("source")
        City source;

        @JsonProperty("takeoff_timestamp")
        ZonedDateTime takeoffTimestamp;

        public TicketFlight(long flightId, City destination, ZonedDateTime landingTimestamp, City source, ZonedDateTime takeoffTimestamp) {
            this.flightId = flightId;
            this.destination = destination;
            this.landingTimestamp = landingTimestamp;
            this.source = source;
            this.takeoffTimestamp = takeoffTimestamp;
        }
    }
}
