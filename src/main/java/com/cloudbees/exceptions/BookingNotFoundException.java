package com.cloudbees.exceptions;

public class BookingNotFoundException extends Exception{
    public BookingNotFoundException(String message) {
        super(message);
    }
}
