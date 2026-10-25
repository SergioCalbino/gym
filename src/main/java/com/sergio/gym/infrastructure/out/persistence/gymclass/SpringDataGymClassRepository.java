package com.sergio.gym.infrastructure.out.persistence.gymclass;


import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataGymClassRepository extends JpaRepository<GymClassEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) //Para concurrencia de escritura en db
    @Query("SELECT g FROM GymClassEntity g WHERE g.id = :id")
    Optional<GymClassEntity> findByIdForUpdate(@Param("id") UUID id);
}
