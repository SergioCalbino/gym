package com.sergio.gym.domain.models;


import com.sergio.gym.domain.enums.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Reservation {
    private final UUID id;
    private final UUID memberId;
    private final UUID gymClassId;
    private final Instant createdAt;
    private ReservationStatus status;


    private Reservation(UUID id, UUID memberId, UUID gymClassId, Instant createdAt, ReservationStatus status) {
        if (id == null ) {
            throw new IllegalArgumentException("Id must not be null");
        }
        this.id = id;

        if (memberId == null ) {
            throw new IllegalArgumentException("MemberId must not be null");
        }
        this.memberId = memberId;

        if (gymClassId == null ) {
            throw new IllegalArgumentException("GymClassId must not be null");
        }
        this.gymClassId = gymClassId;

        if (createdAt == null ) {
            throw new IllegalArgumentException("CreatedAt must not be null");
        }
        this.createdAt = createdAt;

        this.status = ReservationStatus.ACTIVE;
    }


    public Reservation(UUID id, UUID memberId, UUID gymClassId, Instant createdAt) {
        this(id, memberId, gymClassId, createdAt, ReservationStatus.ACTIVE);
    }

    public static Reservation restore (UUID id, UUID memberId, UUID gymClassId, Instant createdAt, ReservationStatus status) {
        return new Reservation(id, memberId, gymClassId, createdAt, status);
    }

    public void cancel() {
        if (this.status == ReservationStatus.CANCELLED) {
            throw  new IllegalStateException("Reservation is already cancelled");
        }
        this.status = ReservationStatus.CANCELLED;

    }
}
