package com.sergio.gym.infrastructure.out.persistence.reservation;

import com.sergio.gym.domain.models.Reservation;

public class ReservationMapper {

    public static ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getMemberId(),
                reservation.getGymClassId(),
                reservation.getCreatedAt(),
                reservation.getStatus()
        );
    }

    public static Reservation toDomain(ReservationEntity reservationEntity) {
        return  Reservation.restore(
                reservationEntity.getId(),
                reservationEntity.getMemberId(),
                reservationEntity.getGymClassId(),
                reservationEntity.getCreatedAt(),
                reservationEntity.getStatus()
        );
    }
}
