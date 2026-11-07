package com.sergio.gym.infrastructure.in.web.gymClass;

import com.sergio.gym.domain.models.GymClass;

import java.util.UUID;

public record CreateGymClassResponse(
        UUID id,
        String name,
        int capacity,
        int availableSpots
) {

    public static CreateGymClassResponse from(GymClass gymClass) {
        return new CreateGymClassResponse(
                gymClass.getId(),
                gymClass.getName(),
                gymClass.getCapacity(),
                gymClass.availableSpots()


        );
    }
}
