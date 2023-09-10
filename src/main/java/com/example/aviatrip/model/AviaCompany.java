package com.example.aviatrip.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "avia_companies")
public class AviaCompany {

    @Id
    private Long id;

    @Column
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "avia_company_representative_id")
    @MapsId
    private AviaCompanyRepresentative representative;

    @OneToMany(mappedBy = "company")
    private Set<Flight> flights = new HashSet<>();

}
