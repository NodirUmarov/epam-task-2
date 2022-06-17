package com.epam.repository.dao.impl;

import com.epam.repository.dao.TagDao;
import com.epam.repository.dao.rowmapper.TagRowMapper;
import com.epam.repository.model.entity.TagEntity;
import com.epam.repository.model.exception.DuplicateEntityException;
import com.epam.repository.model.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 */

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {

    private final String SELECT_BY_ID = "SELECT * FROM tb_tags WHERE id IN (:id);";

    private final String SELECT_BY_NAME = "SELECT * FROM tb_tags WHERE name IN (:name);";

    private final String FIND_ALL_SORTED_PAGED = "SELECT * FROM tb_tags ORDER BY name LIMIT :limit OFFSET :offset";

    private final String EXISTS_BY_NAME = "SELECT EXISTS(SELECT * FROM tb_tags WHERE name IN (:name))";

    private final String INSERT_RETURN_ID = "INSERT INTO tb_tags VALUES (:name) RETURNING id;";

    private final String DELETE_BY_ID = "DELETE FROM tb_tags WHERE id IN (:id);";

    private final TagRowMapper tagRowMapper;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SafeVarargs
    private <T> void checkForNull(T... object) {
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
        namedParameterJdbcTemplate.query(INSERT_RETURN_ID, new MapSqlParameterSource("name", tagEntity.getName()),
                rs -> {
                    tagEntity.setId(rs.getLong(1));
                });
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
        checkForNull(tags.toArray());

        return tags.stream()
                .map(tagEntity -> namedParameterJdbcTemplate.query(SELECT_BY_NAME,
                                new MapSqlParameterSource("name", tagEntity.getName()), tagRowMapper).stream().findFirst()
                        .orElseGet(() -> {
                            namedParameterJdbcTemplate.query(INSERT_RETURN_ID, new MapSqlParameterSource("name", tagEntity.getName()),
                                    rs -> {
                                        tagEntity.setId(rs.getLong(1));
                                    });
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
        } catch (NullPointerException ex) {
            throw new EntityNotFoundException();
        }
    }

}
