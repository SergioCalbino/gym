package com.sergio.gym.application.port.out;

import com.sergio.gym.domain.models.Reservation;

public interface ReservationRepository {
    void save(Reservation reservation);
}
