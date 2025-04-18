package com.cloudbees.controller;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.model.Booking;
import com.cloudbees.service.BookingsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingControllerTest {

    @InjectMocks
    private BookingsController bookingController;

    @Mock
    private BookingsService bookingService;

    @Test
    public void testPurchaseTicket() {
        Booking mockBooking = new Booking();
        mockBooking.setPnrNumber(1L);
        mockBooking.setStart("LD");
        mockBooking.setDestination("FR");
        BookingsRequest request = new BookingsRequest("LD", 1L, 20.0, "FR", Date.from(Instant.now()), "aa");
        when(bookingService.createTicket(any(Booking.class), eq("aa")))
                .thenReturn(mockBooking);

        ResponseEntity<Booking> response = bookingController.bookTicket(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("FR", response.getBody().getDestination());
    }

    @Test
    public void testGetBookingByUser() {
        Booking b1 = new Booking();
        b1.setPnrNumber(1L);

        when(bookingService.getTicketDetails("aa")).thenReturn(List.of(b1));

        ResponseEntity<List<Booking>> response = bookingController.getTicketDetails("aa");

        assertEquals(1, response.getBody().size());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteBooking() {
        doNothing().when(bookingService).removeUserFromTrain("aa");

        ResponseEntity<Void> response = bookingController.removeUser("aa");

        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateSeat() {
        Booking b = new Booking();
        b.setPnrNumber(1L);

        doNothing().when(bookingService).modifyUserSeat("aa", 11L, "A");

        ResponseEntity<Void> response = bookingController.modifyUserSeat("aa", 11L, "A");

        assertEquals(204, response.getStatusCodeValue());
    }
}

