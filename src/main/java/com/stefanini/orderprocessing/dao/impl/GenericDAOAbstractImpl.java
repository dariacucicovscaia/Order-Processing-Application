package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.IGenericDAO;
import com.stefanini.orderprocessing.helper.DataBaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public abstract class GenericDAOAbstractImpl<T> implements IGenericDAO<T> {
    private Class<T> entityClazz;

    protected Statement getConnectionStatement() {
        try {
            return DataBaseConnection.getConnection().createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Environment environment;

    @Autowired
    public final void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setClazz(final Class<T> clazzToSet) {
        entityClazz = clazzToSet;
    }


    @Override
    public List<T> getAll() {
        Field fields[] = entityClazz.getDeclaredFields();
        String sql = "SELECT *  FROM " + getTableName() + ";";
        List<T> entityList = setFieldValues(fields, sql);

        return entityList;
    }


    @Override
    public T update(T entity, int id) {
        Field[] fields = entityClazz.getDeclaredFields();

        String resultToBeUpdated = "";
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (field.getType().getName().equals("int") || field.getType().getName().equals("boolean")) {
                    resultToBeUpdated += "`" + field.getName() + "` = " + value + ",";
                } else {
                    resultToBeUpdated += "`" + field.getName() + "` = '" + value + "',";
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        resultToBeUpdated = resultToBeUpdated.substring(0, resultToBeUpdated.length() - 1);
        resultToBeUpdated += "";

        String update = "UPDATE " + getTableName() + "\n SET " + resultToBeUpdated + " \n WHERE `id`=" + id + ";";

        try {
            getConnectionStatement().executeUpdate(update);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T create(T entity) {
        Field fields[] = entityClazz.getDeclaredFields();
        String insert = "INSERT INTO " + getTableName() + "\r\n" + getColumns(fields) + "\r\n" + getValuesToInsert(fields, entity);

        try {
            getConnectionStatement().executeUpdate(insert);
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T getById(int id) {
        Field fields[] = entityClazz.getDeclaredFields();
        String sql = "SELECT *  FROM " + getTableName() + " WHERE id=" + id + " ;";
        List<T> entity = setFieldValues(fields, sql);

        if (entity.equals(null)) {
            return null;
        } else {
            return entity.get(0);
        }
    }

    @Override
    public int removeById(int id) {
        String sql = "DELETE FROM " + getTableName() + " \r\n 	WHERE id= " + id + " ;";

        try {
            getConnectionStatement().executeUpdate(sql);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getColumns(Field[] fields) {
        String columns = "(";
        for (Field field : fields) {
            columns += field.getName() + ",";
        }
        columns = columns.substring(0, columns.length() - 1);
        columns += ")";

        return columns;
    }

    private String getValuesToInsert(Field[] fields, T entity) {
        String values = "VALUES (";
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (field.getType().getName().equals("int") || field.getType().getName().equals("boolean")) {
                    values += value + ",";
                } else if (field.getType().getName().equals("java.lang.String")) {
                    values += "'" + value + "',";
                } else {
                    values += "'" + value + "',";
                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        values = values.substring(0, values.length() - 1);
        values += ") ";
        return values;
    }

    private String getTableName() {
        return "`" + environment.getProperty("schema_name") + "`." + entityClazz.getSimpleName().toLowerCase();
    }

    protected List<T> setFieldValues(Field[] fields, String sql) {
        List<T> entityList = new ArrayList<T>();
        T t = null;
        try {
            ResultSet result = getConnectionStatement().executeQuery(sql);

            while (result.next()) {
                Constructor constr = entityClazz.getConstructor();
                t = (T) constr.newInstance();
                for (Field field : fields) {
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object fieldValue = result.getObject(fieldName);

                    if (field.getType().isEnum()) {
                        Method valueOf = field.getType().getMethod("valueOf", String.class);
                        Object value = valueOf.invoke(null, fieldValue);
                        field.set(t, value);
                    } else if (field.getType().getName().equals("boolean")) {
                        if (result.getObject(fieldName).equals(true)) {
                            field.set(t, Boolean.TRUE);
                        } else {
                            field.set(t, Boolean.FALSE);
                        }
                    } else field.set(t, fieldValue);
                }
                entityList.add(t);
            }
        } catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(e);
        }

        return entityList;
    }
}
