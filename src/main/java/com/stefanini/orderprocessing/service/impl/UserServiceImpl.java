package com.stefanini.orderprocessing.service.impl;

import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.dao.impl.UserDAOImpl;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.service.UserService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl  implements UserService {
    private final UserDAO<User> userDAO;

    public UserServiceImpl(UserDAOImpl userDAO){
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    @Override
    public Order placeOrder(int userId, Order order){
    order.setUser(userDAO.getById(userId));
        return null;
    }

    @Override
    public User createUser(User user) {
        return userDAO.create(user);
    }
}
