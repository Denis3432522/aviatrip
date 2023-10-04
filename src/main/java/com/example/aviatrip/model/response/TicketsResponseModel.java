package com.example.aviatrip.model.response;

import com.example.aviatrip.model.entity.FlightSeat;

import java.util.List;

public class TicketsResponseModel {

    List<Ticket> tickets;
    public TicketsResponseModel(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
