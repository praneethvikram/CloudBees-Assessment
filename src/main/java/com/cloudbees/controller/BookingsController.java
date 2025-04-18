package com.cloudbees.controller;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.model.Bookings;
import com.cloudbees.service.BookingsService;
import com.cloudbees.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingsController {
    @Autowired
    public BookingsService bookingsService;

    @PostMapping("/createBooking")
    public ResponseEntity<Bookings> bookTicket(@RequestBody BookingsRequest bookingsRequest) {
        Bookings bookings = bookingsService.createTicket(MapperUtil.getBooking(bookingsRequest), bookingsRequest.getUserName());
        return new ResponseEntity<>(bookings, HttpStatus.CREATED);
    }

    @GetMapping("/details/{userName}")
    public ResponseEntity<Bookings> getTicketDetails(@PathVariable String userName) {
        Bookings ticket = bookingsService.getTicketDetails(userName);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bookings>> getAllBookings() {
        return new ResponseEntity<>(bookingsService.getAllBookings(), HttpStatus.OK);
    }

    @DeleteMapping("/remove/{userName}")
    public ResponseEntity<Void> removeUser(@PathVariable String userName) {
        bookingsService.removeUserFromTrain(userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modify-seat/{userName}")
    public ResponseEntity<Void> modifyUserSeat(@PathVariable String userName, @RequestParam Long trainId, @RequestParam String newSectionName) {
        bookingsService.modifyUserSeat(userName, trainId, newSectionName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
