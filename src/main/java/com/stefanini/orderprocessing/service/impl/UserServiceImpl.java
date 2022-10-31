package com.stefanini.orderprocessing.service.impl;

import com.stefanini.orderprocessing.dao.OrderDAO;
import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.dao.impl.UserDAOImpl;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.email.MailSenderService;
import com.stefanini.orderprocessing.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDAO<User> userDAO;
    private final OrderDAO<Order> orderDAO;
    private final MailSenderService emailSenderService;

    public UserServiceImpl(UserDAOImpl userDAO, OrderDAO<Order> orderDAO, MailSenderService emailSenderService) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    @Override
    public Order placeOrder(int userId, Order order) {
        User user = userDAO.getById(userId);

        String email = "New order has been initialized\n\nUsers details:\n" + "Name: " + user.getName() + "\n"
                + "Email: " + user.getEmail() + "\n"
                + "Address: " + user.getAddress() + "\n\n"
                + "Order details:\n" + "Type: " +order.getType() +  "\nStatus: " +order.getStatus() + "\n";
        String subject = "New order";

        emailSenderService.sendMail(email, subject);

        order.setUserId(userId);
        return orderDAO.create(order);
    }

    @Override
    public User createUser(User user) {
        return userDAO.create(user);
    }

    @Override
    public List<Order> getAllUserOrders(int id) {
        return userDAO.getUsersOrders(id);
    }
}
