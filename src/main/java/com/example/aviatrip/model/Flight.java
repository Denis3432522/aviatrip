package com.example.aviatrip.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {

    @Column(name = "flight_id")
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "airplane_name", nullable = false)
    private String airplaneName;

    @Column(name = "takeoff_date", nullable = false)
    private Date takeoffDate;

    @Column(name = "landing_date", nullable = false)
    private Date landingDate;

    @Column(nullable = false)
    private String source;

    @Column(nullable = false)
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false, unique = true)
    private AviaCompany company;

    @OneToMany(mappedBy = "flight")
    private final Set<FlightSeat> seats = new HashSet<>();

    protected Flight() {}

    public Flight(String airplaneName, Date takeoffDate, Date landingDate, String source, String destination, AviaCompany company) {
        this.airplaneName = airplaneName;
        this.takeoffDate = takeoffDate;
        this.landingDate = landingDate;
        this.source = source;
        this.destination = destination;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getAirplaneName() {
        return airplaneName;
    }

    public Date getTakeoffDate() {
        return takeoffDate;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public AviaCompany getCompany() {
        return company;
    }

    public Set<FlightSeat> getSeats() {
        return seats;
    }
}