package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOAbstractImpl<User> implements UserDAO<User> {
    public UserDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setClazz(User.class);

    }
    @Override
    public List<Order> getUsersOrders(int id){
        return  jdbcTemplate.query("SELECT * FROM `order-processing`.order \nWHERE `userId`=" + id, new BeanPropertyRowMapper<>(Order.class));
    }
}
