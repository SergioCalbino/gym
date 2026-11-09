package com.sergio.gym.domain.models;


import com.sergio.gym.domain.exceptions.ClassFullException;
import com.sergio.gym.domain.valueobjects.Capacity;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GymClass {
    private final UUID id;
    private final String name;
    private final Capacity capacity;
    private int occupiedSpots;

    private GymClass(UUID id, String name, Capacity capacity, int occupiedSpots) {
        if (capacity == null) {
            throw new IllegalArgumentException("Capacity must not be null");
        }
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be null or blank");
        }
        if(id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if (occupiedSpots < 0 || occupiedSpots > capacity.value()) {
            throw new IllegalArgumentException("Occupied spots must be between 0 and capacity");
        }

        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.occupiedSpots = occupiedSpots;
    }
    public GymClass(UUID id, String name, Capacity capacity) {
        this(id, name, capacity, 0);   // llama al privado con 0 ocupados
    }

    // Traer una que ya existe en la base, con su estado
    public static GymClass restore(UUID id, String name, Capacity capacity, int occupiedSpots) {
        return new GymClass(id, name, capacity, occupiedSpots);
    }


    public void reserveSpot() {
        if (occupiedSpots >= capacity.value())  {
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
        return capacity.value() - occupiedSpots;
    }



}
