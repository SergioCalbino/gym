package com.sergio.gym.domain.exceptions;

public class ClassFullException extends RuntimeException {
    public ClassFullException(String message) {
        super(message);
    }
}
