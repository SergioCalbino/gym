package com.sergio.gym.domain.models;


import com.sergio.gym.domain.exceptions.ClassFullException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GymClass {
    private final UUID id;
    private final String name;
    private final int capacity;
    private int occupiedSpots;

    private GymClass(UUID id, String name, int capacity, int occupiedSpots) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        if(id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.occupiedSpots = occupiedSpots;
    }
    public GymClass(UUID id, String name, int capacity) {
        this(id, name, capacity, 0);   // llama al privado con 0 ocupados
    }

    // Traer una que ya existe en la base, con su estado
    public static GymClass restore(UUID id, String name, int capacity, int occupiedSpots) {
        return new GymClass(id, name, capacity, occupiedSpots);
    }


    public void reserveSpot() {
        if (occupiedSpots >= capacity)  {
            throw new ClassFullException("There are not enough spots to reserve");
        }
        occupiedSpots++;
    }

    public void releaseSpot() {
        if (occupiedSpots <= 0) {
            throw new IllegalStateException("There are no occupied spots to release");
        }
        occupiedSpots--;
    }

    public int availableSpots() {
        return capacity - occupiedSpots;
    }



}
