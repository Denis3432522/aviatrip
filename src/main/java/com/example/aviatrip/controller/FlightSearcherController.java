package com.example.aviatrip.controller;

import com.example.aviatrip.model.request.TicketFilterModel;
import com.example.aviatrip.model.response.Ticket;
import com.example.aviatrip.service.TicketsSearcherService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public List<Ticket> search(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestBody @Valid TicketFilterModel model) {
        Pageable pageRequest = PageRequest.of(pageNumber, 5);

        return flightSearcherService.getFilteredTickets(model, pageRequest, true);
    }
}
