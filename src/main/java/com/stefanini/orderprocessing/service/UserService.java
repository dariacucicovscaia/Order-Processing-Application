package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;

import java.util.List;

/**
 * Class is user to manage users
 */
public interface UserService {
    /**
     * Gets all the users
     *
     * @return list of all the users
     */
    List<User> getAllUsers();

    /**
     * Creates an order assigned to user
     *
     * @param userId - id of the user
     * @param order  - the new order
     * @return - new order
     */
    Order placeOrder(int userId, Order order);

    /**
     * Creates a new user
     *
     * @param user - new user
     * @return - user
     */
    User createUser(User user);

    /**
     * Gets all orders of a specified user
     *
     * @param id - users id
     * @return - list of users orders
     */
    List<Order> getAllUserOrders(int id);

    User getUserById(int id);
}
