package com.cloudbees.service;

import com.cloudbees.exceptions.*;
import com.cloudbees.model.*;
import com.cloudbees.repository.*;
import com.cloudbees.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingsServiceImpl implements BookingsService {

    @Autowired
    public BookingsRepository bookingsRepository;
    @Autowired
    public UserRepository userRepository;
    @Autowired
    public TrainRepository trainRepository;
    @Autowired
    public SeatRepository seatRepository;
    @Autowired
    public SectionRepository sectionRepository;

    @Override
    public Booking createTicket(Booking booking, String userName) throws NoSeatsAvailableException, UserNotFoundException, TrainNotFoundException {
        User user = userRepository.findById(userName).orElseThrow(() -> new UserNotFoundException("User not found with username " + userName));
        Train train = trainRepository.findById(booking.getTrainNumber()).orElseThrow(() -> new TrainNotFoundException("Train not found with train number " + booking.getTrainNumber()));
        Section availableSection = null;
        Seat availableSeat = null;
        for (Section section : train.getSections()) {
            List<Seat> seats = seatRepository.findBySection_SectionIdAndIsAllocated(section.getSectionId(), false);
            if (!seats.isEmpty()) {
                availableSection = section;
                availableSeat = seats.getFirst();
                break;
            }
        }

        if (availableSeat == null) {
            throw new NoSeatsAvailableException("No Available seats in train " + train.getTrainNumber());
        }

        availableSeat.setAllocated(true);
        seatRepository.save(availableSeat);

        booking.setUser(user);
        booking.setSeat(availableSeat);
        booking.setSectionName(availableSection.getName());

        return bookingsRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingsRepository.findAll();
    }

    @Override
    public List<Booking> getTicketDetails(String userName) {
        return bookingsRepository.findByUser_UserName(userName);
    }

    @Override
    public boolean removeUserFromTrain(String userName, long trainNumber) throws BookingNotFoundException {
        List<Booking> bookings = bookingsRepository.findByUser_UserName(userName).stream().filter(a -> a.getTrainNumber() == trainNumber).toList();

        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No booking found with user name " + userName + " on train number " + trainNumber);
        }

        for (Booking ticket : bookings) {
            // Free the seat and remove the ticket
            Seat seat = ticket.getSeat();
            seat.setAllocated(false);  // Clear seat allocation status
            seatRepository.save(seat);
            bookingsRepository.delete(ticket);
        }

        return true;
    }

    @Override
    public boolean removeUserFromTrainByDate(String userName, long trainNumber, Date travelDate) throws BookingNotFoundException {
        List<Booking> bookings = bookingsRepository.findByUser_UserName(userName).stream().filter(a -> a.getTrainNumber() == trainNumber && DateUtil.isSameDate(travelDate, a.getTravelDate())).toList();

        if (bookings.isEmpty()) {
            throw new BookingNotFoundException("No booking found for user " + userName + " on train number " + trainNumber + " for date " + travelDate);
        }

        for (Booking ticket : bookings) {
            // Free the seat and remove the ticket
            Seat seat = ticket.getSeat();
            seat.setAllocated(false);  // Clear seat allocation status
            seatRepository.save(seat);
            bookingsRepository.delete(ticket);
        }

        return true;
    }

    public Booking modifyUserSeat(String userName, long trainNumber, String newSectionName, Date travelDate) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException, ManyBookingsFoundException {
        List<Booking> ticket = bookingsRepository.findByUser_UserName(userName).stream().filter(a -> a.getTrainNumber() == trainNumber && DateUtil.isSameDate(travelDate, a.getTravelDate())).toList();
        if (ticket.isEmpty()) {
            throw new BookingNotFoundException("No booking found with user name " + userName);
        }
        if (ticket.size() > 1) {
            throw new ManyBookingsFoundException("Too Many bookings found to modify for user " + userName + " for date" + travelDate + " on train number " + trainNumber);
        }
        Optional<Section> newSection = sectionRepository.findByTrain_TrainNumberAndName(trainNumber, newSectionName);
        if (newSection.isEmpty()) {
            throw new SectionNotFoundException("No section found with section Name " + newSectionName);
        }

        Optional<Seat> newSeat = seatRepository.findBySection_SectionIdAndIsAllocated(newSection.get().getSectionId(), false).stream().findFirst();
        if (newSeat.isEmpty()) {
            throw new SeatNotFoundException("No seats found section " + newSectionName);
        }

        Booking result = ticket.getFirst();

        // Free the old seat
        Seat oldSeat = result.getSeat();
        oldSeat.setAllocated(false);
        seatRepository.save(oldSeat);

        // Allocate the new seat
        newSeat.get().setAllocated(true);
        seatRepository.save(newSeat.get());

        // Update the ticket with the new seat
        result.setSeat(newSeat.get());
        result.setSectionName(newSectionName); // Update the section
        bookingsRepository.save(result);
        return result;
    }

}
