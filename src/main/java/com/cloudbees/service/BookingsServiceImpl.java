package com.cloudbees.service;

import com.cloudbees.exceptions.BookingNotFoundException;
import com.cloudbees.exceptions.SeatNotFoundException;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.*;
import com.cloudbees.repository.*;
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
    @Autowired
    public AllocationService allocationService;

    @Override
    public Booking createTicket(Booking booking, String userName) {
        User user = userRepository.findById(userName).orElseThrow(() -> new RuntimeException("User not found with username " + userName));
        Train train = trainRepository.findById(booking.getTrainNumber()).orElseThrow(() -> new RuntimeException("Train not found with train number " + booking.getTrainNumber()));
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
            throw new RuntimeException("No Available seats");
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
    public void removeUserFromTrain(String userName) throws BookingNotFoundException {
        Booking ticket = bookingsRepository.findByUser_UserName(userName).stream().findFirst().orElseThrow(() -> new BookingNotFoundException("No booking found with user name" + userName));
        if (ticket != null) {
            // Free the seat and remove the ticket
            Seat seat = ticket.getSeat();
            seat.setAllocated(false);  // Clear seat allocation status
            seatRepository.save(seat);
            bookingsRepository.delete(ticket);
        }
    }

    public void modifyUserSeat(String userName, Long trainId, String newSectionName, Date travelDate) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException {
        List<Booking> ticket = bookingsRepository.findByUser_UserName(userName).stream().filter(a -> a.getTrainNumber() == trainId && a.getTravelDate().equals(travelDate)).toList();
        if (ticket.isEmpty()) {
            throw new BookingNotFoundException("No booking found with user name" + userName);
        }
        if (ticket.size() > 1) {
            throw new RuntimeException("test");
        }
        Optional<Section> newSection = sectionRepository.findByTrain_TrainNumberAndName(trainId, newSectionName);
        if (newSection.isEmpty()) {
            throw new SectionNotFoundException("No section found with section Name" + newSectionName);
        }

        Optional<Seat> newSeat = seatRepository.findBySection_SectionIdAndIsAllocated(newSection.get().getSectionId(), false).stream().findFirst();
        if (newSeat.isEmpty()) {
            throw new SeatNotFoundException("No seats found section" + newSectionName);
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
    }

}
