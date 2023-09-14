package com.example.aviatrip.service;

import com.example.aviatrip.enumeration.Roles;
import com.example.aviatrip.model.Customer;
import com.example.aviatrip.model.Role;
import com.example.aviatrip.model.User;
import com.example.aviatrip.repository.CustomerRepository;
import com.example.aviatrip.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    CustomerRepository customerRepository;
    @Autowired
    RoleRepository roleRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public void createCustomer(User user) {
        customerRepository.save(new Customer(user));
    }
}
