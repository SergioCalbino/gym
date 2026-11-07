package com.sergio.gym.application.port.in;

import com.sergio.gym.domain.models.GymClass;

public interface CreateGymClassUseCase {
    GymClass create(CreateGymClassCommand command);
}
