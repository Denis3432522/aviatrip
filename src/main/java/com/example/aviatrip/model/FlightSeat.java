package com.example.aviatrip.model;

import com.example.aviatrip.enumeration.FlightSeatClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "flight_seats")
@Immutable
public class FlightSeat {

    @Column(name = "flight_seat_id")
    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    private int position;

    @Column(nullable = false)
    private int price;

    @Column(name = "class", nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightSeatClass seatClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected FlightSeat() {}

    public FlightSeat(int position, int price, FlightSeatClass seatClass, Flight flight) {
        this.position = position;
        this.price = price;
        this.seatClass = seatClass;
        this.flight = flight;
    }

    public Long getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public int getPrice() {
        return price;
    }

    public FlightSeatClass getSeatClass() {
        return seatClass;
    }

    public Flight getFlight() {
        return flight;
    }

    public Customer getCustomer() {
        return customer;
    }
}