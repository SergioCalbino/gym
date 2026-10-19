package com.sergio.gym.domain.exceptions;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException(String message) {
        super(message);
    }
}
