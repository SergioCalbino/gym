package com.sergio.gym.application.service;

import com.sergio.gym.application.port.in.CancelReservationUseCase;
import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.application.port.out.ReservationRepository;
import com.sergio.gym.domain.enums.ReservationStatus;
import com.sergio.gym.domain.exceptions.GymClassNotFoundException;
import com.sergio.gym.domain.exceptions.ReservationNotFoundException;
import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.domain.models.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CancelReservationService implements CancelReservationUseCase {

    private final GymClassRepository gymClassRepository;
    private final ReservationRepository reservationRepository;



    @Override
    public Reservation cancelReservation(UUID reservationId) {
        Reservation reservation = reservationRepository.findByIdForUpdate(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found for reservationId: " + reservationId));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            return  reservation;
        }
        reservation.cancel();
        GymClass gymClass = gymClassRepository.findByIdForUpdate(reservation.getGymClassId())
                .orElseThrow(() -> new GymClassNotFoundException("Gym class not found for reservationId: " + reservationId));

        gymClass.releaseSpot();
        reservationRepository.save(reservation);
        gymClassRepository.save(gymClass);
        return reservation;
    }
}
