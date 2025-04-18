package com.cloudbees.service;

import com.cloudbees.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AllocationServiceImpl implements AllocationService {
    @Autowired
    TrainService trainService;

    @Override
    public boolean isSectionFull(long trainNumber, String section, Date date) {
//        Optional<TrainToSeatMapping> seatMapping = trainSeatMappingRepository.findAll().stream().filter(a -> a.getTrainNumber() == trainNumber && a.getDate().equals(date)).findFirst();
//        if (seatMapping.isPresent()) {
//            Seat seats = seatMapping.get().getSeats();
//            return seats.stream().filter(a -> a.getSection().equals(section)).allMatch(Seat::isAllocated);
//        }
        return false;
    }

    @Override
    public void deleteUser(long trainNumber, String userName) {

    }

    @Override
    public Booking updateSeat(long trainNumber, String userName, String section, int seatId) {
        return null;
    }
}
