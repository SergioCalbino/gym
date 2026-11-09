package com.sergio.gym.infrastructure.out.persistence.gymclass;

import com.sergio.gym.domain.models.GymClass;
import com.sergio.gym.domain.valueobjects.Capacity;

public class GymClassMapper {

    public static GymClassEntity toEntity(GymClass gymClass) {
        return new GymClassEntity(
                gymClass.getId(),
                gymClass.getName(),
                gymClass.getCapacity().value(),
                gymClass.getOccupiedSpots()
        );
    }

    public static GymClass toDomain(GymClassEntity entity) {
        return GymClass.restore(
                entity.getId(),
                entity.getName(),
                new Capacity(entity.getCapacity()),
                entity.getOccupiedSpots()
        );
    }
}
