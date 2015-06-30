package com.prodyna.showcase.pizza.business;

/**
 * Created by dkrizic on 30.06.15.
 */
public class Order {
    private int number;
    private int count;
    private String description;

    public Order() {
        // default
    }

    public Order(int number, int count, String description) {
        this.number = number;
        this.count = count;
        this.description = description;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Order{" +
                "number=" + number +
                ", count=" + count +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (number != order.number) return false;
        if (count != order.count) return false;
        return !(description != null ? !description.equals(order.description) : order.description != null);

    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + count;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

}
