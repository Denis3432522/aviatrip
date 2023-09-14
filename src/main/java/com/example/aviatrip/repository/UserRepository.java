package com.example.aviatrip.repository;

import com.example.aviatrip.model.Customer;
import com.example.aviatrip.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT c FROM Customer c WHERE c.id = :customerId")
    Optional<Customer> findCustomerById(@Param("customerId") Long customerId);

    boolean existsByEmail(String email);

}
