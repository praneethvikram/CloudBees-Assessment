package com.cloudbees.controller;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.exceptions.*;
import com.cloudbees.model.Booking;
import com.cloudbees.service.BookingsService;
import com.cloudbees.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public ResponseEntity<Booking> bookTicket(@RequestBody BookingsRequest bookingsRequest) throws UserNotFoundException, TrainNotFoundException, NoSeatsAvailableException {
        Booking booking = bookingsService.createTicket(MapperUtil.getBooking(bookingsRequest), bookingsRequest.getUser().getUserName());
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

    @DeleteMapping("/remove/{userName}/{trainNumber}")
    public ResponseEntity<String> removeUserBooking(@PathVariable String userName, @PathVariable long trainNumber) throws BookingNotFoundException {
        if (bookingsService.removeUserFromTrain(userName, trainNumber)) {
            return new ResponseEntity<>("Booking removed for the user " + userName + " on train number " + trainNumber, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/removeByDate/{userName}/{trainNumber}")
    public ResponseEntity<String> removeUserBookingForDate(@PathVariable String userName, @PathVariable long trainNumber, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws BookingNotFoundException {
        if (bookingsService.removeUserFromTrainByDate(userName, trainNumber, date)) {
            return new ResponseEntity<>("Booking removed for the user " + userName + " on train number " + trainNumber + " for date " + date, HttpStatus.OK);
        }
        ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modify-seat/{userName}/{trainNumber}")
    public ResponseEntity<Booking> modifyUserSeat(@PathVariable String userName, @PathVariable long trainNumber, @RequestParam String newSectionName, @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws BookingNotFoundException, SectionNotFoundException, SeatNotFoundException, ManyBookingsFoundException {
        Booking updatedBooking =  bookingsService.modifyUserSeat(userName, trainNumber, newSectionName, date);
        return new ResponseEntity<>(updatedBooking, HttpStatus.ACCEPTED);
    }

}
