package com.sergio.gym.domain.models;

import com.sergio.gym.domain.enums.OrderStatus;
import com.sergio.gym.domain.valueobjects.Price;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
public class Order {
    private final UUID id;
    private final UUID memberId;
    private List<OrderItem> items = new ArrayList<>();
    private OrderStatus status;

    public Order(UUID id, UUID memberId) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        if (memberId == null) {
            throw new IllegalArgumentException("memberId cannot be null");
        }
        this.id = id;
        this.memberId = memberId;
        this.status = OrderStatus.PENDING;
    }

    public void addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
       if (this.status == OrderStatus.PAID) {
           throw new IllegalArgumentException("Cannot add an item to paid order");
       }
        items.add(item);
    }

    public Price total() {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : items) {
            total = total.add(item.getPrice().value());

        }
        return new Price(total);
    }

    public Price totalStream() {
        BigDecimal sum = items.stream()
                .map(item -> item.getPrice().value())
                .reduce(BigDecimal.ZERO, (acc, mont) -> acc.add(mont));
        return new Price(sum);

    }

    public void pay() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot pay an empty order");
        }
        if (status == OrderStatus.PAID) {
            throw new IllegalStateException("Cannot pay an paid order");
        }
        status = OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {                          // ← lo escribimos a mano
        return List.copyOf(items);                               //   devuelve una COPIA que no se puede modificar
    }
}
