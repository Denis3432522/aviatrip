package com.example.aviatrip.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "customer")
    private Set<FlightSeat> seats = new HashSet<>();

    protected Customer() {}

    public Customer(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Set<FlightSeat> getSeats() {
        return seats;
    }

    public void setSeats(Set<FlightSeat> seats) {
        this.seats = seats;
    }
}
