package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    Order placeOrder(int userId, Order order);

    User createUser(User user);
}
