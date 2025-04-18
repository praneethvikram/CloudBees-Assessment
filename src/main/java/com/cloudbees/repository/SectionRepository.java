package com.cloudbees.repository;

import com.cloudbees.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByTrain_TrainNumberAndName(Long trainNumber, String name);

}
