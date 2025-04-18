package com.cloudbees.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Booking")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pnrNumber;
    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name")
    private User user;
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;
    private long trainNumber;
    private String start;
    private String destination;
    private double fare;
    private Date travelDate;
    private String sectionName;

    public long getPnrNumber() {
        return pnrNumber;
    }

    public void setPnrNumber(long pnrNumber) {
        this.pnrNumber = pnrNumber;
    }

    public long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
