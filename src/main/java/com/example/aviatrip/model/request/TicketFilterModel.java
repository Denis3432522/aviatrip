package com.example.aviatrip.model.request;

import com.example.aviatrip.config.validation.annotation.EnumString;
import com.example.aviatrip.config.validation.annotation.FutureDateLimit;
import com.example.aviatrip.config.validation.annotation.NotPastDate;
import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.enumeration.FlightSeatClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class TicketFilterModel {

    @NotNull
    @EnumString(enumClazz = City.class)
    private String source;

    @NotNull
    @EnumString(enumClazz = City.class)
    private String destination;

    @JsonProperty("takeoff_date")
    @NotNull
    @NotPastDate
    @FutureDateLimit
    private LocalDate takeOffDate;

    @JsonProperty("companies")
    @Size(min = 1, max = 5)
    private Set<String> companies;

    @JsonProperty("seat_class")
    @EnumString(enumClazz = FlightSeatClass.class)
    private String seatClass = "ECONOMY";

    public City getSource() {
        return City.valueOf(source.toUpperCase());
    }

    public City getDestination() {
        return City.valueOf(destination.toUpperCase());
    }

    public LocalDate getTakeoffDate() {
        return takeOffDate;
    }

    public Optional<Set<String>> getCompanies() {
        return Optional.ofNullable(companies);
    }

    public FlightSeatClass getSeatClass() {
        return FlightSeatClass.valueOf(seatClass.toUpperCase());
    }

    @Override
    public String toString() {
        return "TicketFilterModel{" +
                "source=" + source +
                ", destination=" + destination +
                ", takeOffTimestamp=" + takeOffDate +
                ", companiesNames=" + companies +
                ", seatClasses=" + seatClass +
                '}';
    }
}
