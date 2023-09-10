package com.example.aviatrip.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private Set<FlightSeat> seats = new HashSet<>();
}
