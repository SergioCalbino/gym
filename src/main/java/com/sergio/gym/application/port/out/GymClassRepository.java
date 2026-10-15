package com.sergio.gym.application.port.out;

import com.sergio.gym.domain.models.GymClass;

import java.util.Optional;
import java.util.UUID;

public interface GymClassRepository {
    Optional<GymClass> findById(UUID id);
    void save(GymClass gymClass);
}
