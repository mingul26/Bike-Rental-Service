package com.mingul.bikerentalservice.model;

import javax.persistence.*;

@Entity
public class Damage {
    @Id
    @Column(name = "reservationId")
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "reservationId")
    private Reservation reservation;

    private String description;

    private String status;

    private Float fineAmount;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(Float fineAmount) {
        this.fineAmount = fineAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}

