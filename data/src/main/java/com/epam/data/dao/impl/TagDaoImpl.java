package com.epam.data.dao.impl;

import com.epam.data.dao.TagDao;
import com.epam.data.dao.rowmapper.TagRowMapper;
import com.epam.data.model.entity.TagEntity;
import com.epam.data.model.exception.DuplicateEntityException;
import com.epam.data.model.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.data.dao.impl.query.TagQuery.SELECT_BY_NAME;
import static com.epam.data.dao.impl.query.TagQuery.INSERT;
import static com.epam.data.dao.impl.query.TagQuery.SELECT_BY_ID;
import static com.epam.data.dao.impl.query.TagQuery.DELETE_BY_ID;
import static com.epam.data.dao.impl.query.TagQuery.FIND_ALL_SORTED_PAGED;
import static com.epam.data.dao.impl.query.TagQuery.EXISTS_BY_NAME;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 */

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final TagRowMapper tagRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SafeVarargs
    private <T> void checkForNull(T... object) {
        if (object == null) {
            throw new IllegalArgumentException();
        }
        for (T t : object) {
            if (t == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void checkForDuplicates(TagEntity tagEntity) {
        if (existsByName(tagEntity.getName())) {
            throw new DuplicateEntityException();
        }
    }

    @Override
    public Optional<TagEntity> findById(Long id) throws IllegalArgumentException {
        checkForNull(id);
        return namedParameterJdbcTemplate.query(SELECT_BY_ID, new MapSqlParameterSource("id", id), tagRowMapper)
                .stream().findFirst();
    }

    @Override
    public TagEntity save(TagEntity tagEntity) throws IllegalArgumentException {
        checkForNull(tagEntity);
        checkForDuplicates(tagEntity);
        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT,
                new MapSqlParameterSource("name", tagEntity.getName()), holder, new String[]{"id" });
        tagEntity.setId(Objects.requireNonNull(holder.getKey()).longValue());
        return tagEntity;
    }

    public boolean existsByName(String name) {
        checkForNull(name);
        return namedParameterJdbcTemplate.query(EXISTS_BY_NAME,
                new MapSqlParameterSource("name", name), (rs, rowNum) -> rs.getBoolean(1)).get(0);
    }

    @Override
    public void deleteById(Long id) {
        checkForNull(id);
        namedParameterJdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource("id", id));
    }

    @Override
    public Set<TagEntity> saveAll(Set<TagEntity> tags) {
        checkForNull(tags);
        checkForNull(tags.toArray());

        return tags.stream()
                .map(tagEntity -> namedParameterJdbcTemplate.query(SELECT_BY_NAME,
                                new MapSqlParameterSource("name", tagEntity.getName()), tagRowMapper).stream().findFirst()
                        .orElseGet(() -> {
                            save(tagEntity);
                            return tagEntity;
                        }))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TagEntity> findAllSorted(Integer limit, Integer offset) throws IllegalArgumentException {
        checkForNull(limit, offset);
        return new HashSet<>(namedParameterJdbcTemplate.query(FIND_ALL_SORTED_PAGED,
                new MapSqlParameterSource().addValue("limit", limit).addValue("offset", offset), tagRowMapper));
    }

    @Override
    public TagEntity getById(Long id) throws IllegalArgumentException, EntityNotFoundException {
        checkForNull(id);
        try {
            TagEntity entity = namedParameterJdbcTemplate.query(SELECT_BY_ID,
                    new MapSqlParameterSource("id", id), tagRowMapper).get(0);
            if (entity == null) {
                throw new NullPointerException();
            }
            return entity;
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            throw new EntityNotFoundException();
        }
    }

}
