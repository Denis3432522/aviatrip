package com.example.aviatrip.model;

import jakarta.persistence.*;

@Entity
@Table(name = "flight_seats")
public class FlightSeat {

    @Column(name = "flight_seat_id")
    @Id
    @GeneratedValue
    public Long id;

    @Column
    private int position;

    @Column
    private int price;

    @Column(name = "class")
    private int seatClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;
}