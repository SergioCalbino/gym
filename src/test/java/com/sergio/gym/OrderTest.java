package com.sergio.gym;

import com.sergio.gym.domain.enums.OrderStatus;
import com.sergio.gym.domain.models.Order;
import com.sergio.gym.domain.models.OrderItem;
import com.sergio.gym.domain.valueobjects.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000010");
    private static final UUID MEMBER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");

    private OrderItem item(String description, String price) {
        return new OrderItem(UUID.randomUUID(), description, new Price(new BigDecimal(price)));
    }

    @Test
    void newOrderIsPendingAndEmpty() {
        Order order = new Order(ORDER_ID, MEMBER_ID);

        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertTrue(order.getItems().isEmpty());
    }

    @Test
    void totalIsTheSumOfTheItemPrices() {
        Order order = new Order(ORDER_ID, MEMBER_ID);
        order.addItem(item("Monthly plan", "30000"));
        order.addItem(item("Locker", "5000"));
        order.addItem(item("Towel", "2000"));

        assertEquals(new Price(new BigDecimal("37000")), order.total());
    }

    @Test
    void totalOfAnEmptyOrderIsZero() {
        Order order = new Order(ORDER_ID, MEMBER_ID);

        assertEquals(new Price(BigDecimal.ZERO), order.total());
    }

    @Test
    void payChangesStatusToPaid() {
        Order order = new Order(ORDER_ID, MEMBER_ID);
        order.addItem(item("Monthly plan", "30000"));

        order.pay();

        assertEquals(OrderStatus.PAID, order.getStatus());
    }

    @Test
    void cannotPayAnEmptyOrder() {
        Order order = new Order(ORDER_ID, MEMBER_ID);

        assertThrows(IllegalStateException.class, order::pay);
    }

    @Test
    void cannotPayTwice() {
        Order order = new Order(ORDER_ID, MEMBER_ID);
        order.addItem(item("Monthly plan", "30000"));
        order.pay();

        assertThrows(IllegalStateException.class, order::pay);
    }

    @Test
    void cannotAddItemsToAPaidOrder() {
        Order order = new Order(ORDER_ID, MEMBER_ID);
        order.addItem(item("Monthly plan", "30000"));
        order.pay();

        assertThrows(IllegalStateException.class, () -> order.addItem(item("Towel", "2000")));
    }

    @Test
    void itemsCannotBeModifiedFromOutside() {
        Order order = new Order(ORDER_ID, MEMBER_ID);
        order.addItem(item("Monthly plan", "30000"));

        assertThrows(UnsupportedOperationException.class,
                () -> order.getItems().add(item("Towel", "2000")));
    }
}