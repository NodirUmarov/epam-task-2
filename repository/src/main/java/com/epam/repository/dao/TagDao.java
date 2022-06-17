package com.epam.repository.dao;

import com.epam.repository.model.entity.TagEntity;

import java.util.Set;

/**
 * <p>Extension of {@link com.epam.repository.dao.BaseDao} interface that declares {@link com.epam.repository.model.entity.TagEntity} specific methods.
 *
 * <li><b>saveAll</b>
 * - saves all given {@link com.epam.repository.model.entity.TagEntity TagEntities}. If saved entity is already exists by its id,
 * methods updates only changed fields. Use returned instance for further operations as the save operation might have changed
 * the entity instance completely</li>
 * <li><b>findAllSorted</b>
 * - finds all tags and returns tags in {@link java.util.Set} in natural order</li>
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 * @version 0.1.0
 * @see com.epam.repository.dao.BaseDao
 * @since 0.1.0
 */
public interface TagDao extends BaseDao<Long, TagEntity> {

    /**
     * <p>Saves all {@link com.epam.repository.model.entity.TagEntity TagEntities}.
     *
     * @param tags must not be null
     * @return the saved entities. Will never be null.
     * @throws IllegalArgumentException if arguments or one of the argument is null
     * @since 0.1.0
     */
    Set<TagEntity> saveAll(Set<TagEntity> tags) throws IllegalArgumentException;

    /**
     * <p>Returns all instances of the type {@link com.epam.repository.model.entity.TagEntity}.
     *
     * @param limit  must not be null nor negative or zero values
     * @param offset must not be null nor negative
     * @return Guaranteed to be in natural order
     * @throws IllegalArgumentException if arguments or one of the argument is null
     * @since 0.1.0
     */
    Set<TagEntity> findAllSorted(Integer limit, Integer offset) throws IllegalArgumentException;

}