package com.sergio.gym.infrastructure.in.web.reservation;

import com.sergio.gym.domain.enums.ReservationStatus;
import com.sergio.gym.domain.models.Reservation;

import java.time.Instant;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID memberId,
        UUID gymClassId,
        Instant createdAt,
        ReservationStatus status

) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getMemberId(),
                reservation.getGymClassId(),
                reservation.getCreatedAt(),
                reservation.getStatus()
        );
    }
}
