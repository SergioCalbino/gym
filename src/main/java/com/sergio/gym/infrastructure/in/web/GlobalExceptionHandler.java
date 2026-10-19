package com.sergio.gym.infrastructure.in.web;

import com.sergio.gym.domain.exceptions.AlreadyReservedException;
import com.sergio.gym.domain.exceptions.ClassFullException;
import com.sergio.gym.domain.exceptions.GymClassNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GymClassNotFoundException.class)
    public ProblemDetail handleGymClassNotFoundException(GymClassNotFoundException ex) {
       return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClassFullException.class)
    public ProblemDetail handleClassFullException(ClassFullException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(AlreadyReservedException.class)
    public ProblemDetail handleAlreadyReservedException(AlreadyReservedException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }

}
