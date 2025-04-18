package com.cloudbees.service;

import com.cloudbees.model.*;
import com.cloudbees.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Bookings createTicket(Bookings bookings, String userName) {
        User user = userRepository.findById(userName).orElseThrow(() -> new RuntimeException("User not found with username " + userName));
        Train train = trainRepository.findById(bookings.getTrainNumber()).orElseThrow(() -> new RuntimeException("Train not found with train number " + bookings.getTrainNumber()));
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

        bookings.setUser(user);
        bookings.setSeat(availableSeat);
        bookings.setSectionName(availableSection.getName());

        return bookingsRepository.save(bookings);
    }

    @Override
    public List<Bookings> getAllBookings() {
        return bookingsRepository.findAll();
    }

    @Override
    public Bookings getTicketDetails(String userName) {
        return bookingsRepository.findByUser_UserName(userName).stream().findFirst().orElse(null);
    }

    @Override
    public void removeUserFromTrain(String userName) {
        Bookings ticket = bookingsRepository.findByUser_UserName(userName).stream().findFirst().orElse(null);
        if (ticket != null) {
            // Free the seat and remove the ticket
            Seat seat = ticket.getSeat();
            seat.setAllocated(false);  // Clear seat allocation status
            seatRepository.save(seat);
            bookingsRepository.delete(ticket);
        }
    }

    public void modifyUserSeat(String userName, Long trainId, String newSectionName) {
        Bookings ticket = bookingsRepository.findByUser_UserName(userName).stream().findFirst().orElse(null);
        if (ticket != null) {
            Section newSection = sectionRepository.findByTrain_TrainNumberAndName(trainId, newSectionName).orElseThrow(() -> new RuntimeException("no section found"));
            Seat newSeat = seatRepository.findBySection_SectionIdAndIsAllocated(newSection.getSectionId(), false)
                    .stream().findFirst().orElse(null);

            if (newSeat == null) {
                throw new RuntimeException("No available seats in section " + newSectionName);
            }

            // Free the old seat
            Seat oldSeat = ticket.getSeat();
            oldSeat.setAllocated(false);
            seatRepository.save(oldSeat);

            // Allocate the new seat
            newSeat.setAllocated(true);
            seatRepository.save(newSeat);

            // Update the ticket with the new seat
            ticket.setSeat(newSeat);
            ticket.setSectionName(newSectionName); // Update the section
            bookingsRepository.save(ticket);
        }
    }

}
