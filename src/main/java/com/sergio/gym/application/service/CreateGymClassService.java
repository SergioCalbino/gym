package com.sergio.gym.application.service;



import com.sergio.gym.application.port.in.CreateGymClassCommand;
import com.sergio.gym.application.port.in.CreateGymClassUseCase;
import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.domain.models.GymClass;

import com.sergio.gym.domain.valueobjects.Capacity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CreateGymClassService implements CreateGymClassUseCase {

    private final GymClassRepository gymClassRepository;


    @Override
    public GymClass create(CreateGymClassCommand command) {
        GymClass gymClass = new GymClass(UUID.randomUUID(), command.name(), new Capacity(command.capacity()));
        gymClassRepository.save(gymClass);
        return gymClass;
    }
}
