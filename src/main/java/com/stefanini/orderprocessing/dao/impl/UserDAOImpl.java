package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.domain.enums.OrderType;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOAbstractImpl<User> implements UserDAO<User> {
    public UserDAOImpl() {
        setClazz(User.class);
    }

    @Override
    public List<Order> getUsersOrders(int id) {

        Field fields[] = Order.class.getDeclaredFields();
        String sql = "SELECT *  FROM `order-processing`.order WHERE userId=" + id + " ;";
        List<Order> orderList = new ArrayList<>();

        try {
            ResultSet result = getConnectionStatement().executeQuery(sql);

            while (result.next()) {
                Order order = new Order();

                order.setId(result.getInt("id"));
                order.setStatus(result.getString("status"));
                order.setType(OrderType.valueOf(result.getString("type")));
                order.setPaid(result.getBoolean("isPaid"));
                order.setUserId(result.getInt("userId"));

                orderList.add(order);
            }
        } catch (SQLException | IllegalArgumentException | SecurityException e) {
            throw new RuntimeException(e);
        }

        return orderList;
    }
}
