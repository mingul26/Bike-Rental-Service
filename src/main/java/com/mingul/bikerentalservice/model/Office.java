package com.mingul.bikerentalservice.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Office {
    @Id
    @GeneratedValue
    private Integer id;

    private String managerName;

    private String address;

    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private List<EmailId> emailBook = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private List<PhoneNumber> phoneBook = new ArrayList<>();

    @OneToMany(mappedBy = "sourceLocation")
    private List<Bike> bikes = new ArrayList<>();

    @OneToMany(mappedBy = "targetLocation")
    private List<Bike> incomingBikes = new ArrayList<>();

    @OneToMany(mappedBy = "pickupLocation")
    private List<Reservation> pickups = new ArrayList<>();

    @OneToMany(mappedBy = "returnLocation")
    private List<Reservation> returns = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<EmailId> getEmailBook() {
        return emailBook;
    }

    public void setEmailBook(List<EmailId> emailBook) {
        this.emailBook = emailBook;
    }

    public List<PhoneNumber> getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(List<PhoneNumber> phoneBook) {
        this.phoneBook = phoneBook;
    }

    public List<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(List<Bike> bikes) {
        this.bikes = bikes;
    }

    public List<Reservation> getPickups() {
        return pickups;
    }

    public void setPickups(List<Reservation> pickups) {
        this.pickups = pickups;
    }

    public List<Reservation> getReturns() {
        return returns;
    }

    public void setReturns(List<Reservation> returns) {
        this.returns = returns;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Bike> getIncomingBikes() {
        return incomingBikes;
    }

    public void setIncomingBikes(List<Bike> incomingBikes) {
        this.incomingBikes = incomingBikes;
    }
}
