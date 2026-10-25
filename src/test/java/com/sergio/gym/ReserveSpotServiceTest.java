package com.sergio.gym;


import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.application.port.out.ReservationRepository;
import com.sergio.gym.application.service.ReserveSpotService;
import com.sergio.gym.domain.enums.ReservationStatus;
import com.sergio.gym.domain.exceptions.ClassFullException;
import com.sergio.gym.domain.exceptions.GymClassNotFoundException;
import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.domain.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveSpotServiceTest {

    private static final UUID MEMBER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID CLASS_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final Instant NOW = Instant.parse("2026-10-15T10:00:00Z");

    @Mock
    private GymClassRepository gymClassRepository;
    @Mock
    private ReservationRepository reservationRepository;

    private ReserveSpotService service;

    @BeforeEach
    void setUp() {
        Clock fixedClock = Clock.fixed(NOW, ZoneOffset.UTC);
        service = new ReserveSpotService(gymClassRepository, reservationRepository, fixedClock);
    }

    @Test
    void reserveCreatesAnActiveReservation() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.of(gymClass));

        Reservation reservation = service.reserve(MEMBER_ID, CLASS_ID);

        assertEquals(MEMBER_ID, reservation.getMemberId());
        assertEquals(CLASS_ID, reservation.getGymClassId());
        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
        assertEquals(NOW, reservation.getCreatedAt());
    }

    @Test
    void reserveOccupiesOneSpotAndSavesTheClass() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.of(gymClass));

        service.reserve(MEMBER_ID, CLASS_ID);

        assertEquals(1, gymClass.getOccupiedSpots());
        verify(gymClassRepository).save(gymClass);
    }

    @Test
    void reserveSavesTheReservationItReturns() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.of(gymClass));

        Reservation returned = service.reserve(MEMBER_ID, CLASS_ID);

        ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
        verify(reservationRepository).save(captor.capture());
        assertSame(returned, captor.getValue());
    }

    @Test
    void reservingANonExistingClassThrowsNotFound() {
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.empty());

        assertThrows(GymClassNotFoundException.class, () -> service.reserve(MEMBER_ID, CLASS_ID));

        verify(reservationRepository, never()).save(any());
    }

    @Test
    void reservingAFullClassThrowsAndSavesNothing() {
        GymClass fullClass = new GymClass(CLASS_ID, "Spinning", 1);
        fullClass.reserveSpot();
        when(gymClassRepository.findByIdForUpdate(CLASS_ID)).thenReturn(Optional.of(fullClass));

        assertThrows(ClassFullException.class, () -> service.reserve(MEMBER_ID, CLASS_ID));

        verify(gymClassRepository, never()).save(any());
        verify(reservationRepository, never()).save(any());
    }
}
