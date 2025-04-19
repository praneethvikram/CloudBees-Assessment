package com.cloudbees.exceptions;

public class ManyBookingsFoundException extends Exception {
    public ManyBookingsFoundException(String message) {
        super(message);
    }
}
