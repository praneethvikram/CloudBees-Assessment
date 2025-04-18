package com.cloudbees.service;

import com.cloudbees.model.Booking;

import java.util.Date;

public interface AllocationService {
    boolean isSectionFull(long trainNumber, String section, Date date);

    void deleteUser(long trainNumber, String userName);

    Booking updateSeat(long trainNumber, String userName, String section, int seatId);
}
