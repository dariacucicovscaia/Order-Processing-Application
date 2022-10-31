package com.stefanini.orderprocessing.dao;

import com.stefanini.orderprocessing.domain.Order;

import java.util.List;

/**
 * This class is responsible for database operations with User entity
 *
 * @param <User>
 */
public interface UserDAO<User> extends IGenericDAO<User> {
    /**
     * gets all orders of a specified user
     *
     * @param id - users id
     * @return list of orders
     */
    List<Order> getUsersOrders(int id);
}
