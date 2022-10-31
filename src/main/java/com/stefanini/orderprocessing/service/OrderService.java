package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.domain.Order;

import java.util.List;

/**
 * Class is used to manage orders
 */
public interface OrderService {
    /**
     * Gets all the orders
     *
     * @return list of all orders
     */
    List<Order> getAllOrders();

    /**
     * Creates a new order
     *
     * @param order - new order
     * @return
     */
    Order placeOrder(Order order);

    /**
     * Deletes a order
     *
     * @param id - specified id of the order to be deleted
     * @return id of the order
     */
    int deleteOrder(int id);

    /**
     * Gets order by id
     *
     * @param id - of the order to get
     * @return
     */
    Order getOrderById(int id);

    /**
     * Updates the order Status
     *
     * @param orderId   - id of the order to be updated
     * @param newStatus - the new Status the order should have
     * @return
     */
    Order updateOrderStatus(int orderId, String newStatus);

    /**
     * Pays an order
     *
     * @param orderId - order to be paid
     * @return order
     */
    Order payOrder(int orderId);
}
