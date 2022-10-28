package com.stefanini.orderprocessing.dao;

import java.util.List;

/**
 * Generic class for CRUD entity operations
 *
 * @param <T> class to which we execute the operation
 */
public interface IGenericDAO<T> {
    /**
     * Get all entities.
     *
     * @return list of all entities
     */
    List<T> getAll();

    /**
     * Update entity.
     *
     * @param entity, id
     * @return updated entity
     */
    T update(T entity, int id);

    /**
     * Create entity.
     *
     * @param entity
     * @return created entity
     */
    T create(T entity);

    /**
     * Get entity by id.
     *
     * @param id of entity to be found
     * @return entity
     */
    T getById(int id);

    /**
     * Remove by id.
     *
     * @param id of entity to be found
     * @return id of removed entity
     */
    int removeById(int id);

}
