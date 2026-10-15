package com.sergio.gym;


import com.sergio.gym.domain.enums.ReservationStatus;
import com.sergio.gym.domain.models.Reservation;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private static final UUID RESERVATION_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID MEMBER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID CLASS_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final Instant NOW = Instant.parse("2026-10-15T10:00:00Z");

    @Test
    void newReservationIsActive() {
        Reservation reservation = new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, NOW);

        assertEquals(ReservationStatus.ACTIVE, reservation.getStatus());
        assertEquals(NOW, reservation.getCreatedAt());
    }

    @Test
    void cancelChangesStatusToCancelled() {
        Reservation reservation = new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, NOW);

        reservation.cancel();

        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }

    @Test
    void cannotCancelTwice() {
        Reservation reservation = new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, NOW);
        reservation.cancel();

        assertThrows(IllegalStateException.class, reservation::cancel);
    }

    @Test
    void cannotCreateWithoutMember() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reservation(RESERVATION_ID, null, CLASS_ID, NOW));
    }

    @Test
    void cannotCreateWithoutGymClass() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reservation(RESERVATION_ID, MEMBER_ID, null, NOW));
    }

    @Test
    void cannotCreateWithoutCreationDate() {
        assertThrows(IllegalArgumentException.class,
                () -> new Reservation(RESERVATION_ID, MEMBER_ID, CLASS_ID, null));
    }
}
