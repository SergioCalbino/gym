package com.sergio.gym.application.port.in;

public record CreateGymClassCommand(
        String name,
        int capacity
) {
}
