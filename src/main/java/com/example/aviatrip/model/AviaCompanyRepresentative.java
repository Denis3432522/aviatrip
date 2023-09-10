package com.example.aviatrip.model;

import jakarta.persistence.*;

@Entity
@Table(name = "avia_company_representatives")
@PrimaryKeyJoinColumn(name = "avia_company_representative_id")
public class AviaCompanyRepresentative extends User {

    @OneToOne(mappedBy = "representative")
    private AviaCompany company;
}
