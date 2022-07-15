package com.epam.business.service;

import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.mapper.dtoMapper.TagMapper;
import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.TagRequest;
import com.epam.data.dao.TagDao;
import com.epam.data.model.entity.TagEntity;

import java.util.Set;

/**
 * <p>The interface that defines the tag api of application.
 *
 * <ul>
 *  <li><b>create</b>
 *      - create the tag and return {@link TagDto} it with id</li>
 *  <li><b>getAllTags</b>
 *      - retrieve all {@link TagDto TagDtos}</li>
 * <li><b>deleteById</b>
 *      - delete the tag by its id</li>
 *
 * @see TagDao
 * @see TagEntity
 * @see TagMapper
 * @see CreateTagMapper
 * @version 0.1.0
 * @since 0.1.0
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 7/15/2022
 */
public interface TagService {

    /**
     * <p>Creates given tag, if name provided is unique. If one of the tags or all of them exists,
     * method will return them with their ids</p>
     *
     * @param tags must not be null
     * @return {@link TagDto} with assigned id and wrapped in {@link Set}. Will never be null
     * @since 0.1.0
     */
    Set<TagDto> create(Set<TagRequest> tags);

    /**
     * <p>Returns all {@link TagDto TagDtos} wrapped in {@link Set}.
     * If nothing found, empty set will be returned</p>
     *
     * @param quantity must not be null nor negative or zero values
     * @param page must not be null nor negative
     * @return {@link Set} of {@link TagDto TagDtos} in natural order
     * @since 0.1.0
     */
    Set<TagDto> getAllTags(Integer quantity, Integer page);

    /**
     * <p>Deletes the tag by given id</p>
     *
     * @param id must not be null
     * @throws EntityIdNotFoundException if nothing found by id
     * @since 0.1.0
     */
    void deleteById(Long id) throws EntityIdNotFoundException;

}