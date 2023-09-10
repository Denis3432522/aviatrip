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

    @Column(name = "airplane_name")
    private String airplaneName;

    @Column(name = "takeoff_date")
    private Date takeoffDate;

    @Column(name = "landing_date")
    private Date landingDate;

    @Column
    private String source;

    @Column
    private String destination;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avia_company_id")
    private AviaCompany company;

    @OneToMany(mappedBy = "flight")
    private Set<FlightSeat> seats = new HashSet<>();
}