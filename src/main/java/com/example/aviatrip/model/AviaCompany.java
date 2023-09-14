package com.example.aviatrip.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "avia_companies")
public class AviaCompany {

    @Id
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_representative_id", nullable = false, unique = true)
    @MapsId
    private AviaCompanyRepresentative representative;

    @OneToMany(mappedBy = "company")
    private Set<Flight> flights = new HashSet<>();

    protected AviaCompany() {}

    public AviaCompany(String name, AviaCompanyRepresentative representative) {
        this.name = name;
        this.representative = representative;
    }

    public String getName() {
        return name;
    }

    public AviaCompanyRepresentative getRepresentative() {
        return representative;
    }

    public Set<Flight> getFlights() {
        return flights;
    }

    public void setFlights(Set<Flight> flights) {
        this.flights = flights;
    }
}
