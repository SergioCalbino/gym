package com.sergio.gym.application.port.in;

import com.sergio.gym.domain.models.Reservation;

import java.util.UUID;

public interface CancelReservationUseCase {
    Reservation cancelReservation(UUID reservationId);
}
