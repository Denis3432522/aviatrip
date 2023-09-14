package com.example.aviatrip.model;

import jakarta.persistence.*;

@Entity
@Table(name = "avia_company_representatives")
public class AviaCompanyRepresentative {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @MapsId
    private User user;

    @OneToOne(mappedBy = "representative")
    private AviaCompany company;

    protected AviaCompanyRepresentative() {}

    public AviaCompanyRepresentative(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public AviaCompany getCompany() {
        return company;
    }
}
