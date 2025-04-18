package com.cloudbees.service;

import com.cloudbees.exceptions.BookingNotFoundException;
import com.cloudbees.exceptions.SeatNotFoundException;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Booking;

import java.util.Date;
import java.util.List;

public interface BookingsService {
    Booking createTicket(Booking booking, String userName);

    List<Booking> getAllBookings();

    List<Booking> getTicketDetails(String userName);

    void removeUserFromTrain(String userName) throws BookingNotFoundException;

    void modifyUserSeat(String userName, Long trainId, String newSectionName, Date travelDate) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException;
}
