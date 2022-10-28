package com.stefanini.orderprocessing.dao.impl;

import com.stefanini.orderprocessing.dao.IGenericDAO;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.List;

public abstract class GenericDAOAbstractImpl<T> implements IGenericDAO<T> {
    protected final JdbcTemplate jdbcTemplate;
    private final Environment environment;
    private Class<T> entityClazz;


    public void setClazz(final Class<T> clazzToSet) {
        entityClazz = clazzToSet;
    }


    public GenericDAOAbstractImpl(JdbcTemplate jdbcTemplate, Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }


    @Override
    public List<T> getAll() {
        return jdbcTemplate.query("SELECT * FROM " + getTableName(), new BeanPropertyRowMapper<>(entityClazz));
    }


    @Override
    public T update(T entity, int id) {
        Field[] fields = entityClazz.getDeclaredFields();

        String resultToBeUpdated = "";
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (field.getType().getName().equals("java.lang.String") || field.getType().getName().equals("com.stefanini.orderprocessing.domain.enums.OrderType")) {
                    resultToBeUpdated += "`" + field.getName() + "` = '" + value + "',";
                } else {
                    resultToBeUpdated += "`" + field.getName() + "` = " + value + ",";
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        resultToBeUpdated = resultToBeUpdated.substring(0, resultToBeUpdated.length() - 1);
        resultToBeUpdated += "";


        String update = "UPDATE " + getTableName() + "\n SET " + resultToBeUpdated + " \n WHERE `id`=" + id + ";";
        int rowsAffected = jdbcTemplate.update(update);
        if (rowsAffected == 0) {
            return null;
        } else {
            return entity;
        }
    }

    @Override
    public T create(T entity) {
        Field[] fields = entityClazz.getDeclaredFields();

        String insert = "INSERT INTO " + getTableName() + "\n " + getColumns(fields) + " \n"
                + getValuesToInsert(fields, entity) + ";";

        int fieldsChanged = jdbcTemplate.update(insert);

        if (fieldsChanged == 0) {
            return null;
        } else {
            return entity;
        }
    }

    @Override
    public T getById(int id) {
        T entity = jdbcTemplate.query("SELECT * FROM " + getTableName() + "\nWHERE id=" + id, new BeanPropertyRowMapper<>(entityClazz))
                .stream().findAny().orElse(null);
        if (entity.equals(null)) {
            return null;
        } else {
            return entity;
        }
    }

    @Override
    public int removeById(int id) {
        int fieldsAffected = jdbcTemplate.update("DELETE FROM " + getTableName() + " WHERE id = " + id);
        if (fieldsAffected == 0) {
            return 0;
        } else {
            return id;
        }
    }

    private String getColumns(Field[] fields) {
        String columns = "(";
        for (Field field : fields) {
            field.setAccessible(true);
            columns += "`" + field.getName() + "`,";
        }
        columns = columns.substring(0, columns.length() - 1);
        columns += ")";

        return columns;
    }

    private String getValuesToInsert(Field[] fields, T entity) {
        String values = "VALUES (";
        for (Field field : fields) {
            try {
                Object value = field.get(entity);
                if (field.getType().getName().equals("java.lang.String") || field.getType().getName().equals("com.stefanini.orderprocessing.domain.enums.OrderType")) {
                    values += "'" + value + "',";
                } else {
                    values += value + ",";
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

}
