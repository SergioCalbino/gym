package com.sergio.gym.infrastructure.in.web.reservation;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ReserveSpotRequest(
        @NotNull
        UUID memberId,
        @NotNull
        UUID gymClassId
) {
}
