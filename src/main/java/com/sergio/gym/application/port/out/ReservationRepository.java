package com.sergio.gym.application.port.out;

import com.sergio.gym.domain.models.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);
    Optional<Reservation> findByIdForUpdate(UUID id);
}
