package com.cloudbees.service;

import com.cloudbees.model.Bookings;

import java.util.List;

public interface BookingsService {
    Bookings createTicket(Bookings bookings, String userName);

    List<Bookings> getAllBookings();

    Bookings getTicketDetails(String userName);

    void removeUserFromTrain(String userName);

    void modifyUserSeat(String userName, Long trainId, String newSectionName);
}
