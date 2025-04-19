package com.cloudbees.service;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.exceptions.NoSeatsAvailableException;
import com.cloudbees.exceptions.TrainNotFoundException;
import com.cloudbees.exceptions.UserNotFoundException;
import com.cloudbees.model.*;
import com.cloudbees.repository.*;
import com.cloudbees.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {


    @InjectMocks
    private BookingsService bookingsService = new BookingsServiceImpl();

    @Mock
    private TrainRepository trainRepository;

    @Mock
    private SeatRepository seatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookingsRepository trainTicketRepository;

    @Test
    public void testPurchaseTicket_Success() throws UserNotFoundException, TrainNotFoundException, NoSeatsAvailableException {
        // Arrange
        String userName = "aa";
        long trainNumber = 1L;
        String from = "LD";
        String to = "FR";
        User user = new User();
        user.setUserName(userName);

        Booking booking = MapperUtil.getBooking(new BookingsRequest(from, trainNumber, 20.00, to, Date.from(Instant.now()), user));




        Train train = new Train();
        train.setTrainNumber(trainNumber);
        train.setTrainName("Euro Express");

        Section section = new Section();
        section.setSectionId(1L);
        section.setName("A");
        section.setTrain(train);

        Seat seat = new Seat();
        seat.setId(1L);
        seat.setSeatNumber("A1");
        seat.setAllocated(false);
        seat.setSection(section);

        train.setSections(List.of(section));

        when(userRepository.findById(userName)).thenReturn(Optional.of(user));
        when(trainRepository.findById(trainNumber)).thenReturn(Optional.of(train));
        when(seatRepository.findBySection_SectionIdAndIsAllocated(1L, false)).thenReturn(List.of(seat));
        when(seatRepository.save(any(Seat.class))).thenReturn(seat);
        when(trainTicketRepository.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Booking result = bookingsService.createTicket(booking, userName);
        Assert.notNull(result.getSeat().getSeatNumber(), "");
        // Assert
    }

    @Test
    public void testPurchaseTicket_NoAvailableSeats_ThrowsException() {
        // Arrange
        String userName = "aa";
        long trainNumber = 1L;

        User user = new User();
        user.setUserName(userName);

        Train train = new Train();
        train.setTrainNumber(trainNumber);
        train.setTrainName("Euro Express");

        Section section = new Section();
        section.setSectionId(1L);
        section.setName("A");
        section.setTrain(train);

        train.setSections(List.of(section));
        Booking booking = MapperUtil.getBooking(new BookingsRequest("from", trainNumber, 20.00, "to", Date.from(Instant.now()), user));

        when(userRepository.findById(userName)).thenReturn(Optional.of(user));
        when(trainRepository.findById(trainNumber)).thenReturn(Optional.of(train));
        when(seatRepository.findBySection_SectionIdAndIsAllocated(1L, false)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            bookingsService.createTicket(booking, "FR");
        });
    }
}
