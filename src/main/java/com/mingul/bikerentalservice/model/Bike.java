package com.mingul.bikerentalservice.model;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Bike {
    @Id
    @GeneratedValue
    private Integer id;

    private String brand;

    private String model;

    private String color;

    @ColumnDefault(value = "true")
    private Boolean availability;

    @ManyToOne
    @JoinColumn(name = "sourceLocation")
    private Office sourceLocation;

    @ManyToOne
    @JoinColumn(name = "targetLocation")
    private Office targetLocation;

    @OneToMany(mappedBy = "bike")
    private List<Reservation> reservations = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime registeredAt;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public void setAvailability(Boolean availability) {
        this.availability = availability;
    }

    public Office getSourceLocation() {
        return sourceLocation;
    }

    public void setSourceLocation(Office sourceLocation) {
        this.sourceLocation = sourceLocation;
    }

    public Office getTargetLocation() {
        return targetLocation;
    }

    public void setTargetLocation(Office targetLocation) {
        this.targetLocation = targetLocation;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDateTime registeredAt) {
        this.registeredAt = registeredAt;
    }
}
