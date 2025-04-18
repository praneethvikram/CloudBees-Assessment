package com.cloudbees.service;

import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Seat;
import com.cloudbees.model.Train;

import java.util.List;

public interface TrainService {
    List<Train> getAllTrainDetails();

    public List<Seat> getAvailableSeats(Long trainId, String sectionName) throws SectionNotFoundException;
}
