package com.cloudbees.exceptions;

public class NoSeatsAvailableException extends Exception{
    public NoSeatsAvailableException(String message) {
        super(message);
    }
}
