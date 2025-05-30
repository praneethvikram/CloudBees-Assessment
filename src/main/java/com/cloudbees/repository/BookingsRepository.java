package com.cloudbees.repository;

import com.cloudbees.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingsRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUser_UserName(String userName);

}
