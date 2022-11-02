package com.stefanini.orderprocessing.service.impl;

import com.stefanini.orderprocessing.dao.OrderDAO;
import com.stefanini.orderprocessing.dao.impl.OrderDAOImpl;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.email.MailSenderService;
import com.stefanini.orderprocessing.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDAO<Order> orderDAO;
    private final MailSenderService emailSenderService;

    public OrderServiceImpl(OrderDAOImpl orderDAO, MailSenderService emailSenderService) {
        this.orderDAO = orderDAO;
        this.emailSenderService = emailSenderService;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDAO.getAll();
    }

    @Override
    public Order placeOrder(Order order) {
        return orderDAO.create(order);
    }

    @Override
    public int deleteOrder(int id) {
        return orderDAO.removeById(id);
    }

    @Override
    public Order getOrderById(int id) {
        return orderDAO.getById(id);
    }

    @Override
    public Order updateOrderStatus(int orderId, String newStatus) {
        Order order = getOrderById(orderId);
        order.setStatus(newStatus);

        String email = "Order's status has been changed\n\nOrder details:\n" + "Type: " + order.getType() + "\n"
                + "Status: " + newStatus + "\n";
        String subject = "Order Status Changed";

        emailSenderService.sendMail(email, subject);

        return orderDAO.update(order ,orderId);
    }

    @Override
    public Order payOrder(int orderId) {
        Order order = getOrderById(orderId);
        order.setIsPaid(true);

        return orderDAO.update(order ,orderId);
    }


}
