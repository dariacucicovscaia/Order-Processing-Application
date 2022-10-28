package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends GenericDAOAbstractImpl<User> implements UserDAO<User> {
    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, Environment environment) {
        super(jdbcTemplate, environment);
        setClazz(User.class);
    }
}
