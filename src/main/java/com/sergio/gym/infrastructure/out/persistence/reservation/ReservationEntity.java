package com.sergio.gym.infrastructure.out.persistence.reservation;


import com.sergio.gym.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {

    @Id
    private UUID id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Column(name = "gym_class_id", nullable = false)
    private UUID gymClassId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;                       // Hibernate mapea Instant a TIMESTAMPTZ solo

    @Enumerated(EnumType.STRING)                     // guarda el enum como texto ("ACTIVE"), no como número
    @Column(name = "status", nullable = false, length = 20)
    private ReservationStatus status;

}
