package com.epam.data.dao;

import com.epam.data.model.entity.TagEntity;
import com.epam.data.model.entity.GiftCertificateEntity;
import com.epam.lib.constants.SortType;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * <p>Extension of {@link BaseDao} interface that declares {@link GiftCertificateEntity} specific methods.
 *
 * <ul>
 *  <li><b>findByName</b>
 *      - retrieves {@link GiftCertificateEntity} by its name</li>
 *  <li><b>existsById</b>
 *      - returns whether {@link GiftCertificateEntity} with given id exists</li>
 *  <li><b>findByName</b>
 *      - retrieves all {@link GiftCertificateEntity GiftCertificateEntities} by its tag</li>
 * <li><b>saveAll</b>
 *      - saves all given entities</li>
 * <li><b>findAllSorted</b>
 *      - finds all {@link GiftCertificateEntity GiftCertificateEntities} in natural order</li>
 * </ul>
 *
 * @see BaseDao
 * @version 0.1.0
 * @since 0.1.0
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 */
public interface GiftCertificateDao extends BaseDao<Long, GiftCertificateEntity> {

    /**
     * <p>Retrieves an entity wrapped in Optional by its name.</p>
     *
     * @param name must not be null
     * @return {@link GiftCertificateEntity} with given id wrapped in {@link java.util.Optional} or {@link Optional#empty()} if none found
     * @throws IllegalArgumentException if id is null
     * @since 0.1.0
     */
    Optional<GiftCertificateEntity> findByName(String name) throws IllegalArgumentException;

    /**
     * <p>Returns all instances of the type {@link GiftCertificateEntity} with the given tag.
     * If tag is not found, no entities are returned for the tag
     *
     * Note that the order of elements can be configured by {@link SortType}.</p>
     *
     * @param tag must not be null
     * @param limit must not be null nor negative or zero values
     * @param offset must not be null nor negative
     * @param sortType for configuring the order of elements. Can be {@link SortType#ASC}, {@link SortType#DESC}, {@link SortType#NONE}
     * @return all {@link GiftCertificateEntity GiftCertificateEntities} in specified order,
     * from specified offset and following limited quantity
     * @throws IllegalArgumentException if arguments or one of the argument is null, limit or offset is negative or limit is zero
     * @since 0.1.0
     */
    List<GiftCertificateEntity> findByTag(String tag, Integer limit, Integer offset, SortType sortType);

    /**
     * <p>Returns whether an entity with the given id exists</p>
     *
     *
     * @param id must not be null
     * @return {@code true} if an entity with the given id exists, {@code false} otherwise
     * @throws IllegalArgumentException if id is null
     * @since 0.1.0
     */
    boolean existsById(Long id) throws IllegalArgumentException;

    /**
     * <p>Returns an instances of the type {@link TagEntity} after untagging.
     *
     * @param id   must not be null nor negative or zero values
     * @param tags elements must not be null
     * @return updated entity
     * @throws IllegalArgumentException if arguments or one of the argument is null, or id is zero or negative
     * @since 0.1.0
     */
    GiftCertificateEntity untagCertificate(Long id, Set<String> tags) throws IllegalArgumentException;

    /**
     * <p>Returns an instances of the type {@link TagEntity} after adding tags.
     *
     * @param id   must not be null nor negative or zero values
     * @param tags elements must not be null
     * @return updated entity
     * @throws IllegalArgumentException if arguments or one of the argument is null, or id is zero or negative
     * @since 0.1.0
     */
    GiftCertificateEntity addTag(Long id, Set<String> tags) throws IllegalArgumentException;

}