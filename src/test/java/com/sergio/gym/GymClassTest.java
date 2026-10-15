package com.sergio.gym;



import com.sergio.gym.domain.exceptions.ClassFullException;
import com.sergio.gym.domain.models.GymClass;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GymClassTest {

    private static final UUID CLASS_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");

    @Test
    void reserveSpotIncreasesOccupiedSpots() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);

        gymClass.reserveSpot();

        assertEquals(1, gymClass.getOccupiedSpots());
        assertEquals(1, gymClass.availableSpots());
    }

    @Test
    void cannotReserveSpotWhenClassIsFull() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 1);
        gymClass.reserveSpot();

        assertThrows(ClassFullException.class, gymClass::reserveSpot);
    }

    @Test
    void cannotReleaseSpotWhenClassIsEmpty() {
        GymClass gymClass = new GymClass(CLASS_ID, "Spinning", 2);

        assertThrows(IllegalStateException.class, gymClass::releaseSpot);
    }

    @Test
    void cannotCreateWithZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass(CLASS_ID, "Spinning", 0));
    }

    @Test
    void cannotCreateWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass(CLASS_ID, "  ", 10));
    }

    @Test
    void cannotCreateWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass(null, "Spinning", 10));
    }
}
