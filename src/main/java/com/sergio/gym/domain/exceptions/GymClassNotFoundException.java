package com.sergio.gym.domain.exceptions;

public class GymClassNotFoundException extends RuntimeException {
    public GymClassNotFoundException(String message) {
        super(message);
    }
}
