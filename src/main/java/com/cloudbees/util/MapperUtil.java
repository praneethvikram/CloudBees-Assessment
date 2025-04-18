package com.cloudbees.util;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.dto.UserRequest;
import com.cloudbees.model.Booking;
import com.cloudbees.model.User;

public class MapperUtil {
    public static User getUser(UserRequest userRequest) {
        User user = new User();
        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setAddress(userRequest.getAddress());
        return user;
    }

    public static Booking getBooking(BookingsRequest bookingsRequest) {
        Booking booking = new Booking();
        booking.setStart(bookingsRequest.getSource());
        booking.setDestination(bookingsRequest.getDestination());
        booking.setFare(bookingsRequest.getPrice());
        booking.setTrainNumber(bookingsRequest.getTrainNumber());
        booking.setTravelDate(bookingsRequest.getDate());
        return booking;
    }
}
