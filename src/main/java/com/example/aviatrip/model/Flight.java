package com.example.aviatrip.model;

import com.example.aviatrip.enumeration.City;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
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
    private String airplaneModel;

    @Column(name = "takeoff_date", nullable = false)
    private ZonedDateTime takeoffDate;

    @Column(name = "landing_date", nullable = false)
    private ZonedDateTime landingDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City source;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City destination;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    private AviaCompany company;

    @Column(name = "seat_count")
    private int seatCount;

    @OneToMany(mappedBy = "flight")
    private final Set<FlightSeat> seats = new HashSet<>();

    protected Flight() {}

    public Flight(String airplaneModel, ZonedDateTime takeoffDate, ZonedDateTime landingDate, City source, City destination, int seatCount, AviaCompany company) {
        this.airplaneModel = airplaneModel;
        this.takeoffDate = takeoffDate;
        this.landingDate = landingDate;
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

    public ZonedDateTime getTakeoffDate() {
        return takeoffDate;
    }

    public ZonedDateTime getLandingDate() {
        return landingDate;
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