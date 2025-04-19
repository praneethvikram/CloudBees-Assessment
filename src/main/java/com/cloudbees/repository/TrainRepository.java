package com.cloudbees.repository;

import com.cloudbees.dto.SeatDetails;
import com.cloudbees.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
    @Query("SELECT new com.cloudbees.dto.SeatDetails(b.seat, b.user, b.travelDate) " +
            "FROM Booking b " +
            "WHERE b.seat.isAllocated = true " +
            "AND b.seat.section.id = :sectionId " +
            "AND b.seat.section.train.trainNumber = :trainNumber")
    List<SeatDetails> getSeatDetailsByTrainAndSection(long trainNumber, Long sectionId);
}
