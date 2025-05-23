package com.cloudbees.service;

import com.cloudbees.dto.SeatDetails;
import com.cloudbees.exceptions.SectionNotFoundException;
import com.cloudbees.model.Seat;
import com.cloudbees.model.Section;
import com.cloudbees.model.Train;
import com.cloudbees.repository.SeatRepository;
import com.cloudbees.repository.SectionRepository;
import com.cloudbees.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {
    @Autowired
    public TrainRepository trainRepository;
    @Autowired
    public SectionRepository sectionRepository;
    @Autowired
    public SeatRepository seatRepository;

    @Override
    public List<Train> getAllTrainDetails() {
        return trainRepository.findAll();
    }

    public List<Seat> getAvailableSeats(long trainNumber, String sectionName) throws SectionNotFoundException {
        Section section = sectionRepository.findByTrain_TrainNumberAndName(trainNumber, sectionName).orElseThrow(() -> new SectionNotFoundException("No section found with name" + sectionName));
        return seatRepository.findBySection_SectionIdAndIsAllocated(section.getSectionId(), false);
    }

    @Override
    public List<SeatDetails> getAllocatedSeatsByTrainAndSection(long trainNumber, String sectionName) throws SectionNotFoundException {
        Section section = sectionRepository.findByTrain_TrainNumberAndName(trainNumber, sectionName).orElseThrow(() -> new SectionNotFoundException("No section found with name" + sectionName));
        return trainRepository.getSeatDetailsByTrainAndSection(trainNumber, section.getSectionId());
    }
}
