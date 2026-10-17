package com.sergio.gym.infrastructure.out.persistence.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, UUID> {
}
