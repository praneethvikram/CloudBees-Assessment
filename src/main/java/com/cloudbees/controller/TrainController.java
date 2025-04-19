package com.cloudbees.controller;

import com.cloudbees.dto.SeatDetails;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Seat;
import com.cloudbees.model.Train;
import com.cloudbees.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/train")
public class TrainController {
    @Autowired
    public TrainService trainService;

    @GetMapping("/all")
    public ResponseEntity<List<Train>> getAllTrainDetails() {
        return new ResponseEntity<>(trainService.getAllTrainDetails(), HttpStatus.FOUND);
    }

    @GetMapping("/availableSeats/{trainNumber}/{sectionName}")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable long trainNumber, @PathVariable String sectionName) throws SectionNotFoundException {
        List<Seat> seats = trainService.getAvailableSeats(trainNumber, sectionName);
        return new ResponseEntity<>(seats, HttpStatus.OK);
    }

    @GetMapping("/bookedSeats/{trainNumber}/{sectionName}")
    public ResponseEntity<List<SeatDetails>> getAllocatedSeatsByTrainAndSection(@PathVariable long trainNumber, @PathVariable String sectionName) throws SectionNotFoundException {
        List<SeatDetails> results = trainService.getAllocatedSeatsByTrainAndSection(trainNumber, sectionName);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
