package com.sergio.gym;

import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.application.port.out.ReservationRepository;
import com.sergio.gym.application.service.CancelReservationService;
import com.sergio.gym.domain.enums.ReservationStatus;
import com.sergio.gym.domain.exceptions.ReservationNotFoundException;
import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.domain.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelReservationServiceTest {

    private static final UUID RESERVATION_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID MEMBER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID CLASS_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final Instant NOW = Instant.parse("2026-10-31T10:00:00Z");

    @Mock private GymClassRepository gymClassRepository;
    @Mock private ReservationRepository reservationRepository;

    private CancelReservationService service;

    @BeforeEach
    void setUp() {
        service = new CancelReservationService(gymClassRepository, reservationRepository);
    }

    @Test
    void cancellingAnActiveReservationCancelsItAndReleasesTheSpot() {
        Reservation reservation = new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, NOW);
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);
        gymClass.reserveSpot();   // la clase tiene 1 ocupado: el de esta reserva

        when(reservationRepository.findByIdForUpdate(RESERVATION_ID)).thenReturn(Optional.of(reservation));
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.of(gymClass));

        Reservation result = service.cancelReservation(RESERVATION_ID);

        assertEquals(ReservationStatus.CANCELLED, result.getStatus());
        assertEquals(0, gymClass.getOccupiedSpots());   // el cupo se liberó
        verify(reservationRepository).save(reservation);
        verify(gymClassRepository).save(gymClass);
    }

    @Test
    void cancellingAnAlreadyCancelledReservationReturnsItWithoutTouchingTheClass() {
        Reservation reservation = new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, NOW);
        reservation.cancel();   // ya estaba cancelada (el segundo click)

        when(reservationRepository.findByIdForUpdate(RESERVATION_ID)).thenReturn(Optional.of(reservation));

        Reservation result = service.cancelReservation(RESERVATION_ID);

        assertEquals(ReservationStatus.CANCELLED, result.getStatus());
        verify(gymClassRepository, never()).findByIdForUpdate(any());   // ni siquiera busca la clase
        verify(gymClassRepository, never()).save(any());                // no libera otro cupo
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void cancellingANonExistingReservationThrowsNotFound() {
        when(reservationRepository.findByIdForUpdate(RESERVATION_ID)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> service.cancelReservation(RESERVATION_ID));

        verify(gymClassRepository, never()).save(any());
    }
}