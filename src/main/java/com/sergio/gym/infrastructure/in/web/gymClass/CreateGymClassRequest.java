package com.sergio.gym.infrastructure.in.web.gymClass;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateGymClassRequest(
        @NotBlank String name,
        @NotNull @Positive Integer capacity
) {
}
