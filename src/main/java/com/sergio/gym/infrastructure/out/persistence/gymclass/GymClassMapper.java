package com.sergio.gym.infrastructure.out.persistence.gymclass;

import com.sergio.gym.domain.models.GymClass;

public class GymClassMapper {

    public static GymClassEntity toEntity(GymClass gymClass) {
        return new GymClassEntity(
                gymClass.getId(),
                gymClass.getName(),
                gymClass.getCapacity(),
                gymClass.getOccupiedSpots()
        );
    }

    public static GymClass toDomain(GymClassEntity entity) {
        return GymClass.restore(
                entity.getId(),
                entity.getName(),
                entity.getCapacity(),
                entity.getOccupiedSpots()
        );
    }
}
