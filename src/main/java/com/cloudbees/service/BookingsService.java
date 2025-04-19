package com.cloudbees.service;

import com.cloudbees.exceptions.*;
import com.cloudbees.model.Booking;

import java.util.Date;
import java.util.List;

public interface BookingsService {
    Booking createTicket(Booking booking, String userName) throws NoSeatsAvailableException, UserNotFoundException, TrainNotFoundException;

    List<Booking> getAllBookings();

    List<Booking> getTicketDetails(String userName);

    boolean removeUserFromTrain(String userName, long trainNumber) throws BookingNotFoundException;

    boolean removeUserFromTrainByDate(String userName, long trainNumber, Date date) throws BookingNotFoundException;

    Booking modifyUserSeat(String userName, long trainNumber, String newSectionName, Date travelDate) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException, ManyBookingsFoundException;
}
