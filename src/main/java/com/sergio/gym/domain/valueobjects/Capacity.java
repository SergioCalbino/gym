package com.sergio.gym.domain.valueobjects;

public record Capacity(int value) {
    public Capacity {
        if (value <= 0) throw new IllegalArgumentException("Capacity must be greater than zero");
    }
}
