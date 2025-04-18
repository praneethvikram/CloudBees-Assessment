package com.cloudbees.util;

import com.cloudbees.dto.BookingsRequest;
import com.cloudbees.dto.UserRequest;
import com.cloudbees.model.Bookings;
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

    public static Bookings getBooking(BookingsRequest bookingsRequest) {
        Bookings bookings = new Bookings();
        bookings.setStart(bookingsRequest.getSource());
        bookings.setDestination(bookingsRequest.getDestination());
        bookings.setFare(bookingsRequest.getPrice());
        bookings.setTrainNumber(bookingsRequest.getTrainNumber());
        bookings.setTravelDate(bookingsRequest.getDate());
        return bookings;
    }
}
