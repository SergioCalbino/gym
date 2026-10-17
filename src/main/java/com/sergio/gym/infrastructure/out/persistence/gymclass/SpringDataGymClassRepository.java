package com.sergio.gym.infrastructure.out.persistence.gymclass;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataGymClassRepository extends JpaRepository<GymClassEntity, UUID> {
}
