package com.example.aviatrip.model.entity;

import com.example.aviatrip.enumeration.FlightSeatClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "flight_seats")
public class FlightSeat {

    @Column(name = "flight_seat_id")
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String position;

    @Column(name = "is_window_seat",nullable = false)
    private boolean isWindowSeat;

    @Column(nullable = false)
    private int price;

    @Column(name = "class", nullable = false)
    @Enumerated(EnumType.STRING)
    @JsonProperty("seat_class")
    private FlightSeatClass seatClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonIgnore
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    private Customer customer;

    protected FlightSeat() {}

    public FlightSeat(String position, boolean isWindowSeat, int price, FlightSeatClass seatClass, Flight flight) {
        this.position = position;
        this.isWindowSeat = isWindowSeat;
        this.price = price;
        this.seatClass = seatClass;
        this.flight = flight;
    }

    public long getId() {
        return id;
    }

    public String getPosition() {
        return position;
    }

    public boolean isWindowSeat() {
        return isWindowSeat;
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

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "FlightSeat{" +
                "id=" + id +
                ", position=" + position +
                ", isWindowSeat=" + isWindowSeat +
                ", price=" + price +
                ", seatClass=" + seatClass +
                '}';
    }
}