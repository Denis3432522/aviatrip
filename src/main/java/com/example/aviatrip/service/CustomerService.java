package com.example.aviatrip.service;

import com.example.aviatrip.model.entity.Customer;
import com.example.aviatrip.model.entity.User;
import com.example.aviatrip.repository.*;
import com.example.aviatrip.repository.flight.FlightRepository;
import com.example.aviatrip.repository.flight.FlightSeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final FlightSeatRepository flightSeatRepository;
    private final FlightRepository flightRepository;

    public CustomerService(CustomerRepository customerRepository, RoleRepository roleRepository, FlightSeatRepository flightSeatRepository, FlightRepository flightRepository) {
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.flightSeatRepository = flightSeatRepository;
        this.flightRepository = flightRepository;
    }

    @Transactional
    public void createCustomer(User user) {
        customerRepository.save(new Customer(user));
    }
}
