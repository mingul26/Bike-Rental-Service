package com.mingul.bikerentalservice.model;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Integer id;

    private Float amount;

    private String status;

    @CreationTimestamp
    private Timestamp pickupTimestamp;

    @UpdateTimestamp
    private Timestamp returnTimestamp;

    @ManyToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "pickupLocation")
    private Office pickupLocation;

    @ManyToOne
    @JoinColumn(name = "returnLocation")
    private Office returnLocation;

    @ManyToOne
    @JoinColumn(name = "bikeId")
    private Bike bike;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Office getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Office pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Office getReturnLocation() {
        return returnLocation;
    }

    public void setReturnLocation(Office returnLocation) {
        this.returnLocation = returnLocation;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Timestamp getPickupTimestamp() {
        return pickupTimestamp;
    }

    public void setPickupTimestamp(Timestamp pickupTimestamp) {
        this.pickupTimestamp = pickupTimestamp;
    }

    public Timestamp getReturnTimestamp() {
        return returnTimestamp;
    }

    public void setReturnTimestamp(Timestamp returnTimestamp) {
        this.returnTimestamp = returnTimestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
