package com.example.aviatrip.config.requestmodel;

import com.example.aviatrip.config.validation.annotation.EnumString;
import com.example.aviatrip.enumeration.City;
import com.example.aviatrip.enumeration.FlightSeatClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;

import java.time.ZonedDateTime;
import java.util.Map;

@Validated
public class FlightModel {

    @JsonProperty("airplane_model")
    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "must be between 2 and 32 symbols")
    private String airplaneModel;

    @JsonProperty("takeoff_timestamp")
    @NotNull
    @Future
    private ZonedDateTime takeoffTimestamp;

    @JsonProperty("landing_timestamp")
    @NotNull
    @Future
    private ZonedDateTime landingTimestamp;

    @EnumString(enumClazz = City.class, propertyName = "city")
    private String source;

    @EnumString(enumClazz = City.class, propertyName = "city")
    private String destination;

    @NotEmpty(message = "must have at least one flight seat class configured")
    private Map<@EnumString(enumClazz = FlightSeatClass.class, propertyName = "class") String, @Valid FlightSeatSectionModel> seats;

    public Map<String, FlightSeatSectionModel> getSeats() {
        return seats;
    }

    @JsonProperty("airplane_model")
    public String getAirplaneModel() {
        return airplaneModel;
    }

    @JsonProperty("takeoff_timestamp")
    public ZonedDateTime getTakeoffTimestamp() {
        return takeoffTimestamp;
    }

    @JsonProperty("landing_timestamp")
    public ZonedDateTime getLandingTimestamp() {
        return landingTimestamp;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }
}