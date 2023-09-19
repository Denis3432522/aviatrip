package com.example.aviatrip.model;

import com.example.aviatrip.enumeration.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
@Immutable
public class Flight {

    @Column(name = "flight_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "airplane_model", nullable = false)
    @JsonProperty(value = "airplane_model")
    private String airplaneModel;

    @Column(name = "takeoff_timestamp", nullable = false)
    @JsonProperty(value = "takeoff_timestamp")
    private ZonedDateTime takeoffTimestamp;

    @Column(name = "landing_timestamp", nullable = false)
    @JsonProperty(value = "landing_timestamp")
    private ZonedDateTime landingTimestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonDeserialize()
    private City source;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City destination;

    @Column(name = "seat_count")
    @JsonProperty(value = "seat_count")
    private int seatCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    @JsonIgnore
    private AviaCompany company;

    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private final Set<FlightSeat> seats = new HashSet<>();

    protected Flight() {}

    public Flight(String airplaneModel, ZonedDateTime takeoffTimestamp, ZonedDateTime landingTimestamp, City source, City destination, int seatCount, AviaCompany company) {
        this.airplaneModel = airplaneModel;
        this.takeoffTimestamp = takeoffTimestamp;
        this.landingTimestamp = landingTimestamp;
        this.source = source;
        this.destination = destination;
        this.seatCount = seatCount;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getAirplaneModel() {
        return airplaneModel;
    }

    public ZonedDateTime getTakeoffTimestamp() {
        return takeoffTimestamp;
    }

    public ZonedDateTime getLandingTimestamp() {
        return landingTimestamp;
    }

    public City getSource() {
        return source;
    }

    public City getDestination() {
        return destination;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public AviaCompany getCompany() {
        return company;
    }

    public Set<FlightSeat> getSeats() {
        return seats;
    }
}