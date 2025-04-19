package com.cloudbees.dto;

import com.cloudbees.model.Seat;
import com.cloudbees.model.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SeatDetails {
    private Seat seat;
    private User user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date travelDate;

    public SeatDetails(Seat seat, User user, Date travelDate) {
        this.seat = seat;
        this.user = user;
        this.travelDate = travelDate;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }
}
