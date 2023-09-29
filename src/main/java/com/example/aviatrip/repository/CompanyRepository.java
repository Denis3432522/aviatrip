package com.example.aviatrip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.aviatrip.model.entity.AviaCompany;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<AviaCompany, Long> {

    boolean existsByName(String companyName);
}
