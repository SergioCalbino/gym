package com.sergio.gym.infrastructure.out.persistence.order;


import com.sergio.gym.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {

    @Id
    private UUID id;

    @Column(name = "member_id", nullable = false)
    private UUID memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private OrderStatus status;

    @OneToMany(
            mappedBy = "order",            // el dueño de la relación es el campo "order" de OrderItemEntity
            cascade = CascadeType.ALL,     // lo que le pasa a la orden, les pasa a sus ítems
            orphanRemoval = true           // si un ítem sale de la lista, se borra de la base
    )
    private List<OrderItemEntity> items = new ArrayList<>();

    public void addItem(OrderItemEntity item) {
        items.add(item);       // la orden conoce al ítem
        item.setOrder(this);   // el ítem conoce a su orden (este lado es el que escribe order_id)
    }
}
