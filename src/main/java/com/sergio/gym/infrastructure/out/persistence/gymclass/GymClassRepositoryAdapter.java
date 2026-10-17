package com.sergio.gym.infrastructure.out.persistence.gymclass;

import com.sergio.gym.application.port.out.GymClassRepository;
import com.sergio.gym.domain.models.GymClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class GymClassRepositoryAdapter implements GymClassRepository {

    private final SpringDataGymClassRepository springDataGymClassRepository;

    @Override
    public Optional<GymClass> findById(UUID id) {
        Optional<GymClass> gymClass = springDataGymClassRepository.findById(id)
                .map((GymClassMapper::toDomain));
        return gymClass;

    }

    @Override
    public void save(GymClass gymClass) {
        springDataGymClassRepository.save(GymClassMapper.toEntity(gymClass));

    }
}
