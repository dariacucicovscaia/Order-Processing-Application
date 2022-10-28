package com.stefanini.orderprocessing.controller;

import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.service.UserService;
import com.stefanini.orderprocessing.service.impl.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/myOrders/{userId}")
    public List<Order> getAllUserOrders(@PathVariable int userId){
        return userService.getAllUserOrders(userId);
    }

    @PostMapping("/placeOrder/{userId}")
    public Order placeOrder(@PathVariable int userId, @RequestBody Order order){
        return userService.placeOrder(userId, order);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

}
