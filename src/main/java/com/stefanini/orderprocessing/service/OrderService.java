package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.domain.Order;
import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order placeOrder(Order order);
    int deleteOrder(int id);
    Order getOrderById(int id);
    Order updateOrderStatus(int orderId, String newStatus);

}
