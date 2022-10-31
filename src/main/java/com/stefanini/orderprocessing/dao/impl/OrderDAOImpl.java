package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.OrderDAO;
import com.stefanini.orderprocessing.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDAOImpl extends GenericDAOAbstractImpl<Order> implements OrderDAO<Order> {
    @Autowired
    public OrderDAOImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        setClazz(Order.class);
    }
}
