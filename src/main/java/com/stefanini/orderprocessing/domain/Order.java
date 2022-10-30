package com.stefanini.orderprocessing.domain;

import com.stefanini.orderprocessing.domain.enums.OrderType;

public class Order {
    private int id;
    private String status;
    private OrderType type;
    private boolean isPaid;
    private int userId;

    public Order() {
    }

    public Order(String status, OrderType type, boolean isPaid, int userId) {
        this.status = status;
        this.type = type;
        this.isPaid = isPaid;
        this.userId = userId;
    }

    public Order(int id, String status, OrderType type, boolean isPaid, int userId) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.isPaid = isPaid;
        this.userId = userId;
    }

    public Order(int id, String status, OrderType type, boolean isPaid, User user) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.isPaid = isPaid;
        this.userId = user.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Order markPaid() {
        isPaid = true;
        return this;
    }
    @Override
    public String toString() {
        return "Order{" +
                "status='" + status + '\'' +
                ", type=" + type +
                ", userId=" + userId +
                '}';
    }
}
