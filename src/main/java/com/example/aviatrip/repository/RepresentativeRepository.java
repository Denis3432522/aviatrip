package com.example.aviatrip.repository;

import com.example.aviatrip.model.AviaCompanyRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepresentativeRepository extends JpaRepository<AviaCompanyRepresentative, Long> {

}
