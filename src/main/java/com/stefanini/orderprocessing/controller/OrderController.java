package com.stefanini.orderprocessing.controller;

import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.service.OrderService;
import com.stefanini.orderprocessing.service.impl.OrderServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{id}")
    public int deleteOrder( @PathVariable int id){
        return orderService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    public Order getOrderById( @PathVariable int id){
        return orderService.getOrderById(id);
    }


    @PutMapping ("/{orderId}/{newStatus}")
    public Order changeOrderStatus(@PathVariable int orderId, @PathVariable String newStatus){
        return orderService.updateOrderStatus( orderId,newStatus);
    }

    @PutMapping ("/pay/{orderId}")
    public Order payOrder(@PathVariable int orderId){
        return orderService.payOrder( orderId);
    }
}
