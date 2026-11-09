package com.sergio.gym.domain.models;

import com.sergio.gym.domain.valueobjects.Price;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderItem {
    private final UUID id;
    private final String description;
    private final Price price;

    public OrderItem(UUID id, String description, Price price) {
        if(id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if(description == null) {
            throw new IllegalArgumentException("Description must not be null");

        }
        if (price == null) {
            throw new IllegalArgumentException("Price must not be null");
        }
        this.id = id;
        this.description = description;
        this.price = price;
    }
}
