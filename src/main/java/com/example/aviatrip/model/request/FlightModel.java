package com.example.aviatrip.model.request;

import com.example.aviatrip.config.validation.annotation.EnumString;
import com.example.aviatrip.config.validation.annotation.FutureDateLimit;
import com.example.aviatrip.config.validation.annotation.FutureTimeOffset;
import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.enumeration.FlightSeatClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

public class FlightModel {

    @JsonProperty("airplane_model")
    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "must be between 2 and 32 symbols")
    private String airplaneModel;

    @JsonProperty("takeoff_timestamp")
    @NotNull
    @FutureTimeOffset
    @FutureDateLimit
    private LocalDateTime takeoffTimestamp;

    @JsonProperty("landing_timestamp")
    @NotNull
    private LocalDateTime landingTimestamp;

    @NotNull
    @EnumString(enumClazz = City.class, propertyName = "city")
    private String source;

    @NotNull
    @EnumString(enumClazz = City.class, propertyName = "city")
    private String destination;

    @NotEmpty(message = "must have at least one flight seat class configured")
    private Map<@NotNull @EnumString(enumClazz = FlightSeatClass.class, propertyName = "class") String, @Valid FlightPassengerSectionPriceModel> sections;

    public Map<String, FlightPassengerSectionPriceModel> getSections() {
        return sections;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public ZonedDateTime getTakeoffTimestamp() {
        return ZonedDateTime.of(takeoffTimestamp, ZoneId.of("UTC"));
    }

    public ZonedDateTime getLandingTimestamp() {
        return ZonedDateTime.of(landingTimestamp, ZoneId.of("UTC"));
    }

    public City getSource() {
        return City.valueOf(source.toUpperCase());
    }

    public City getDestination() {
        return City.valueOf(destination.toUpperCase());
    }
}
