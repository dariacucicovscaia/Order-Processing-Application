package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        List<Order> orderList = new ArrayList<Order>();
        Order order = null;
        try {
            ResultSet result = getConnectionStatement().executeQuery(sql);

            while (result.next()) {
                Constructor constr = Order.class.getConstructor();
                order = (Order) constr.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = result.getObject(fieldName);

                    if (field.getType().isEnum()) {
                        Method valueOf = field.getType().getMethod("valueOf", String.class);
                        Object value = valueOf.invoke(null, fieldValue);
                        field.set(order, value);
                    } else if (field.getType().getName().equals("boolean")) {
                        if (result.getObject(fieldName).equals(true)) {
                            field.set(order, Boolean.TRUE);
                        } else {
                            field.set(order, Boolean.FALSE);
                        }
                    } else field.set(order, fieldValue);
                }
                orderList.add(order);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

        return orderList;
    }
}
