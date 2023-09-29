package com.example.aviatrip.model.entity;

import com.example.aviatrip.enumeration.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private long id;

    @Column(name = "takeoff_timestamp", nullable = false)
    @JsonProperty(value = "takeoff_timestamp")
    private ZonedDateTime takeoffTimestamp;

    @Column(name = "landing_timestamp", nullable = false)
    @JsonProperty(value = "landing_timestamp")
    private ZonedDateTime landingTimestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City source;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City destination;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AviaCompany company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    @JsonIgnore
    private Airplane airplane;

    @OneToMany(mappedBy = "flight")
    @JsonIgnore
    private final Set<FlightSeat> seats = new HashSet<>();

    protected Flight() {}

    public Flight(Airplane airplane, ZonedDateTime takeoffTimestamp, ZonedDateTime landingTimestamp, City source, City destination, AviaCompany company) {
        this.airplane = airplane;
        this.takeoffTimestamp = takeoffTimestamp;
        this.landingTimestamp = landingTimestamp;
        this.source = source;
        this.destination = destination;
        this.company = company;
    }

    public long getId() {
        return id;
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

    public AviaCompany getCompany() {
        return company;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public Set<FlightSeat> getSeats() {
        return seats;
    }
}