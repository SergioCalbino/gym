package com.sergio.gym.application.service;


import com.sergio.gym.application.port.in.ReserveSpotUseCase;
import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.application.port.out.ReservationRepository;

import com.sergio.gym.domain.exceptions.GymClassNotFoundException;
import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.domain.models.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReserveSpotService implements ReserveSpotUseCase {

    private final GymClassRepository gymClassRepository;
    private final ReservationRepository reservationRepository;
    private final Clock clock;

    @Override
    public Reservation reserve(UUID memberId, UUID gymClassId) {
        GymClass gymClass = gymClassRepository.findByIdForUpdate(gymClassId) //Busca la clase y la bloqued, si llegan 10 a la vez, 9 esperan aca (Concurrencia)
                .orElseThrow(() -> new GymClassNotFoundException("Gym class not found " + gymClassId));

        gymClass.reserveSpot(); // 2. el que tiene el turno pregunta: ¿hay lugar? (con el dato actualizado)

        gymClassRepository.save(gymClass); // 3. guarda el cupo ocupado

        Reservation reservation = new Reservation(UUID.randomUUID(), memberId, gymClassId, Instant.now(clock));

        reservationRepository.save(reservation); // 4. guarda la reserva

        return reservation;
    }
}
