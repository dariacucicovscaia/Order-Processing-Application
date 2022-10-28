package com.stefanini.orderprocessing.dao;

import com.stefanini.orderprocessing.domain.Order;

import java.util.List;

public interface UserDAO<User> extends IGenericDAO<User> {
    List<Order> getUsersOrders(int id);
}
