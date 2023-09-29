package com.example.aviatrip.model.entity;

import com.example.aviatrip.enumeration.FlightSeatClass;
import com.example.aviatrip.model.entity.Airplane;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "airplane_seat_sections")
public class AirplanePassengerSection {

    @Column(name = "airplane_seat_section_id")
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "seat_class")
    @Enumerated(EnumType.STRING)
    @NotNull
    private FlightSeatClass seatClass;

    @Column(name = "seat_count")
    @JsonProperty("seat_count")
    @Min(value = 2, message = "the minimal seat count must be {value}")
    @Max(value = 200, message = "the maximal seat count must be {value}")
    private int seatCount;

    @Column(name = "seat_row_count")
    @JsonProperty("seat_row_count")
    @Min(value = 2, message = "the minimal row seat count must be {value}")
    @Max(value = 10, message = "the maximal row seat count must be {value}")
    private int rowSeatCount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    @JsonIgnore
    private Airplane airplane;

    public AirplanePassengerSection() {}

    public AirplanePassengerSection(FlightSeatClass seatClass, int seatCount, int rowSeatCount) {
        this.seatClass = seatClass;
        this.seatCount = seatCount;
        this.rowSeatCount = rowSeatCount;
    }

    public long getId() {
        return id;
    }

    public FlightSeatClass getSeatClass() {
        return seatClass;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public int getRowSeatCount() {
        return rowSeatCount;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    @JsonProperty("seat_class")
    //@EnumString(enumClazz = FlightSeatClass.class)
    public void setSeatClass(String seatClass) {
        this.seatClass = FlightSeatClass.valueOf(seatClass.toUpperCase());
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
}
