package com.cloudbees.service;

import com.cloudbees.model.Booking;

import java.util.List;

public interface BookingsService {
    Booking createTicket(Booking booking, String userName);

    List<Booking> getAllBookings();

    List<Booking> getTicketDetails(String userName);

    void removeUserFromTrain(String userName);

    void modifyUserSeat(String userName, Long trainId, String newSectionName);
}
