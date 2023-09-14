package com.example.aviatrip.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_seats")
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
    private int seatClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false, unique = true)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    protected FlightSeat() {}

    public FlightSeat(Long id, int position, int price, int seatClass, Flight flight) {
        this.id = id;
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

    public int getSeatClass() {
        return seatClass;
    }

    public Flight getFlight() {
        return flight;
    }

    public Customer getCustomer() {
        return customer;
    }
}