package com.cloudbees.service;

import com.cloudbees.model.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AllocationServiceImpl implements AllocationService {

    @Override
    public boolean isSectionFull(long trainNumber, String section, Date date) {
        return false;
    }

}
