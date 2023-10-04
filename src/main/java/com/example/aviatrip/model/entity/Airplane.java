package com.example.aviatrip.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Column(name = "airplane_id")
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "must be between 2 and 32 symbols and not consist of special symbols")
    private String model;

    @Column
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int capacity;

    @OneToMany(mappedBy = "airplane", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @NotEmpty(message = "must have at least one flight seat section configured")
    @JsonIgnore
    private Set<@Valid AirplanePassengerSection> sections;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    @JsonIgnore
    private AviaCompany company;

    protected Airplane() {}

    public Airplane(String model, int capacity, AviaCompany company) {
        this.model = model;
        this.capacity = capacity;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public int getCapacity() {
        return capacity;
    }

    public Set<AirplanePassengerSection> getSections() {
        return sections;
    }

    public AviaCompany getCompany() {
        return company;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCompany(AviaCompany company) {
        this.company = company;
    }
}
