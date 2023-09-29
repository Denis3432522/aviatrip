package com.example.aviatrip.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class FlightPassengerSectionPriceModel {

    @JsonProperty("seat_price")
    @Min(value = 10, message = "the minimal price must be {value}$")
    @Max(value = 20_000, message = "the maximal price must be {value}$")
    private int seatPrice;

    @JsonProperty("window_seat_price")
    @Min(value = 12, message = "the minimal price must be {value}$")
    @Max(value = 22_000, message = "the maximal price must be {value}$")
    private int windowSeatPrice;

    public int getSeatPrice() {
        return seatPrice;
    }

    public int getWindowSeatPrice() {
        return windowSeatPrice;
    }

    @Override
    public String toString() {
        return "FlightPassengerSectionPriceModel{" +
                "seatPrice=" + seatPrice +
                ", windowSeatPrice=" + windowSeatPrice +
                '}';
    }
}
