package com.example.aviatrip.service;

import com.example.aviatrip.config.exception.BadRequestException;
import com.example.aviatrip.model.request.TicketFilterModel;
import com.example.aviatrip.model.response.Ticket;
import com.example.aviatrip.repository.custom.TicketSearcherRepository;
import com.example.aviatrip.repository.flight.FlightRepository;
import com.example.aviatrip.repository.flight.FlightSeatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketsSearcherService {

    private final FlightSeatRepository flightSeatRepository;
    public final TicketSearcherRepository ticketSearcherRepository;
    private final FlightRepository flightRepository;

    public TicketsSearcherService(FlightSeatRepository flightSeatRepository, TicketSearcherRepository ticketSearcherRepository, FlightRepository flightRepository) {
        this.flightSeatRepository = flightSeatRepository;
        this.ticketSearcherRepository = ticketSearcherRepository;
        this.flightRepository = flightRepository;
    }

    public List<Ticket> getFilteredTickets(TicketFilterModel model, Pageable pageable, boolean orderByPriceAsc) {
        if(model.getDestination().equals(model.getSource()))
            throw new BadRequestException("source city must not be equal to a destination city");

        return ticketSearcherRepository.findAll(model, pageable, orderByPriceAsc);
    }
}
