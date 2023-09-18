package com.example.aviatrip.config.requestmodel;

import com.example.aviatrip.config.validation.annotation.EnumString;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class FlightSeatSectionModel {

    @Min(value = 10, message = "the minimal price must be {value}$")
    @Max(value = 20_000, message = "the maximal price must be {value}$")
    private int price;

    @Min(value = 1, message = "the minimal count must be {value}")
    @Max(value = 200, message = "the maximal count must be {value}")
    private int count;

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "FlightSeatSectionModel{" +
                "price=" + price +
                ", count=" + count +
                '}';
    }
}
