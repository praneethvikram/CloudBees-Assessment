package com.cloudbees.service;

import com.cloudbees.dto.SeatDetails;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Seat;
import com.cloudbees.model.Train;

import java.util.List;

public interface TrainService {
    List<Train> getAllTrainDetails();
    List<Seat> getAvailableSeats(long trainNumber, String sectionName) throws SectionNotFoundException;
    List<SeatDetails> getAllocatedSeatsByTrainAndSection(long trainNumber, String sectionName) throws SectionNotFoundException;

}
