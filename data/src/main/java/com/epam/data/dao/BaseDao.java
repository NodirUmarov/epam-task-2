package com.epam.data.dao;

import com.epam.data.model.exception.EntityNotFoundException;

import java.util.Optional;

/**
 * <p> Interface for generic CRD operations.
 * The purpose of this class is to provide core necessary methods for this project</p>
 *
 * <ul>
 *  <li><b>findById</b>
 *      - retrieves an entity by its id</li>
 *  <li><b>save</b>
 *      - Saves a given entity.</li>
 * <li><b>deleteById</b>
 *      - deletes the entity with the given id</li>
 * </ul>
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 * @version 0.1.0
 * @since 0.1.0
 */
public interface BaseDao<ID, T> {

    /**
     * <p>Retrieves an entity wrapped in Optional by its id.</p>
     *
     * @param id must not be null
     * @return entity with given id wrapped in {@link java.util.Optional} or {@link Optional#empty()} if none found
     * @throws IllegalArgumentException if id is null
     * @since 0.1.0
     */
    Optional<T> findById(ID id) throws IllegalArgumentException;

    /**
     * <p>Retrieves an entity by its id.</p>
     *
     * @param id must not be null
     * @return entity with given id
     * @throws IllegalArgumentException if id is null
     * @throws EntityNotFoundException if no entity found
     * @since 0.1.0
     */
    T getById(Long id) throws IllegalArgumentException, EntityNotFoundException;

    /**
     * <p>Saves a given entity. If saved entity is already exists by its id, methods updates only changed fields.
     * Use returned instance for further operations as the save operation might have changed the entity instance completely</p>
     *
     * @param entity must not be null
     * @return the saved entity. Will never be null
     * @throws IllegalArgumentException if given entity is null
     * @since 0.1.0
     */
    T save(T entity) throws IllegalArgumentException;

    /**
     * <p>Deletes the entity with the given id</p>
     *
     * @param id must not be null
     * @throws IllegalArgumentException if given id is null
     * @since 0.1.0
     */
    void deleteById(ID id) throws IllegalArgumentException;

}