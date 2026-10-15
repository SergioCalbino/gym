package com.sergio.gym.application.port.in;

import com.sergio.gym.domain.models.Reservation;

import java.util.UUID;

public interface ReserveSpotUseCase {
    Reservation reserve(UUID memberId, UUID gymClassId);
}
