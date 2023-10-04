package com.example.aviatrip.controller;

import com.example.aviatrip.model.request.TicketFilterModel;
import com.example.aviatrip.model.response.Ticket;
import com.example.aviatrip.model.response.TicketsResponseModel;
import com.example.aviatrip.repository.CompanyRepository;
import com.example.aviatrip.repository.flight.FlightRepository;
import com.example.aviatrip.service.TicketsSearcherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class FlightSearcherController {

    TicketsSearcherService flightSearcherService;

    public FlightSearcherController(TicketsSearcherService flightSearcherService) {
        this.flightSearcherService = flightSearcherService;
    }

    @PostMapping
    public TicketsResponseModel search(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestBody @Valid TicketFilterModel model) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);

        List<Ticket> tickets = flightSearcherService.getFilteredTickets(model, pageRequest, true);

        return new TicketsResponseModel(tickets);
    }
}
