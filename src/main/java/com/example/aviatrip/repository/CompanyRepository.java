package com.example.aviatrip.repository;

import com.example.aviatrip.model.entity.AviaCompany;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<AviaCompany, Long> {

    boolean existsByName(String companyName);

    SomeData getNameByFlightsId(long id);

    interface SomeData {

        @Value("#target.name")
        String getName();
    }
}
