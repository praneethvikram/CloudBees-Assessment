package com.cloudbees.dto;

import java.util.Date;

public class BookingsRequest {

    private String source;
    private long trainNumber;
    private double price;
    private String destination;
    private String userName;
    private Date date;

    public BookingsRequest(String source, long trainNumber, double price, String destination, Date date, String userName) {
        this.source = source;
        this.trainNumber = trainNumber;
        this.price = price;
        this.destination = destination;
        this.date = date;
        this.userName = userName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public long getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(long trainNumber) {
        this.trainNumber = trainNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
