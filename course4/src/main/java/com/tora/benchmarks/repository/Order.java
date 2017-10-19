package com.tora.benchmarks.repository;

import java.util.Objects;

/**
 * @author Ovidiu Maja<ovi.dan89@gmail.com>
 * @version Oct 18, 2017
 */
public class Order implements Comparable<Order> {
    private final long id;
    private final long price;
    private final long quantity;

    public Order(long value) {
        this(value, value, value);
    }

    public Order(long id, long price, long quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Order) {
            final Order that = (Order) o;
            return Objects.equals(this.id, that.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public int compareTo(Order o) {
        return Long.compare(this.id, o.id);
    }

    @Override
    public String toString() {
        return String.format("%s[id: %d, price: %d, qty: %d]", getClass().getSimpleName(), id, price, quantity);
    }
}
