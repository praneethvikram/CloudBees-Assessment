package com.cloudbees.controller;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.exceptions.BookingNotFoundException;
import com.cloudbees.exceptions.SeatNotFoundException;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Booking;
import com.cloudbees.service.BookingsService;
import com.cloudbees.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    public BookingsService bookingsService;

    @PostMapping("/createBooking")
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingsRequest bookingsRequest) {
        Booking booking = bookingsService.createTicket(MapperUtil.getBooking(bookingsRequest), bookingsRequest.getUserName());
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/details/{userName}")
    public ResponseEntity<List<Booking>> getTicketDetails(@PathVariable String userName) {
        List<Booking> ticket = bookingsService.getTicketDetails(userName);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return new ResponseEntity<>(bookingsService.getAllBookings(), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userName}")
    public ResponseEntity<Void> removeUser(@PathVariable String userName) throws BookingNotFoundException {
        bookingsService.removeUserFromTrain(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modify-seat/{userName}/{travelDate}")
    public ResponseEntity<Void> modifyUserSeat(@PathVariable String userName, @RequestParam Long trainId, @RequestParam String newSectionName,@PathVariable Date travelDate) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException {
        bookingsService.modifyUserSeat(userName, trainId, newSectionName, travelDate);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
