package com.cloudbees.repository;

import com.cloudbees.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findBySection_SectionIdAndIsAllocated(Long sectionId, boolean isAllocated);
}
