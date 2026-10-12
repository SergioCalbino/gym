package com.sergio.gym;



import com.sergio.gym.domain.exceptions.ClassFullException;
import com.sergio.gym.domain.models.GymClass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GymClassTest {

    @Test
    void reserveSpotIncreasesOccupiedSpots() {
        GymClass gymClass = new GymClass("c1", "Spinning", 2);

        gymClass.reserveSpot();

        assertEquals(1, gymClass.getOccupiedSpots());
        assertEquals(1, gymClass.availableSpots());
    }

    @Test
    void cannotReserveSpotWhenClassIsFull() {
        GymClass gymClass = new GymClass("c1", "Spinning", 1);
        gymClass.reserveSpot();

        assertThrows(ClassFullException.class, gymClass::reserveSpot);
    }

    @Test
    void cannotReleaseSpotWhenClassIsEmpty() {
        GymClass gymClass = new GymClass("c1", "Spinning", 2);

        assertThrows(IllegalStateException.class, gymClass::releaseSpot);
    }

    @Test
    void cannotCreateWithZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass("c1", "Spinning", 0));
    }

    @Test
    void cannotCreateWithoutName() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass("c1", "  ", 10));
    }

    @Test
    void cannotCreateWithoutId() {
        assertThrows(IllegalArgumentException.class, () -> new GymClass(null, "Spinning", 10));
    }
}
