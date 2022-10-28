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

    @PostMapping("/placeOrder")
    public String placeOrder( @RequestBody Order order){
        return orderService.placeOrder(order).toString();
    }

    @DeleteMapping("/{id}")
    public int placeOrder( @PathVariable int id){
        return orderService.deleteOrder(id);
    }

    @GetMapping("/{id}")
    public Order getOrderById( @PathVariable int id){
        return orderService.getOrderById(id);
    }

    @PutMapping ("/{id}")
    public Order updateOrder(@RequestBody Order order, @PathVariable int id){
        return orderService.updateOrderStatus(order, id);
    }
}
