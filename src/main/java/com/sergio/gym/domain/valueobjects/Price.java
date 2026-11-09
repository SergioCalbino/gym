package com.sergio.gym.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Price(BigDecimal value) {
    public Price {
        if (value == null) throw new IllegalArgumentException("Price must not be null");
        if (value.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price must not be negative");
        value = value.setScale(2, RoundingMode.HALF_UP);   // siempre 2 decimales
    }
}
