package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.rowmapperimpl.TagRowMapper;
import com.epam.esm.model.entity.TagEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDaoImpl implements TagDao {

    @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TagEntity> findById(Long id) {
        String sql = "SELECT * FROM tb_tags WHERE id IN (?);";
        return jdbcTemplate
                .query(sql, new TagRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public TagEntity save(TagEntity tagEntity) {
        String sql = "INSERT INTO tb_tags VALUES (?) RETURNING id";
        jdbcTemplate.query(sql, rs -> {
            tagEntity.setId(rs.getLong(1));
        }, tagEntity.getName());
        return tagEntity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tb_tags WHERE id = ?";
        jdbcTemplate.update(sql);
    }

    @Override
    public Set<TagEntity> saveAll(Set<TagEntity> tags) {
        String sqlSelect = "SELECT id, name FROM tb_tags WHERE name IN (?);";
        String sqlInsert = "INSERT INTO tb_tags(name) VALUES (?) RETURNING id;";

        return tags
                .stream()
                .map(tagEntity -> jdbcTemplate
                        .query(sqlSelect, new TagRowMapper(), tagEntity.getName())
                        .stream()
                        .findFirst()
                        .orElseGet(() -> {
                            jdbcTemplate.query(sqlInsert, rs -> {
                                tagEntity.setId(rs.getLong(1));
                            }, tagEntity.getName());
                            return tagEntity;
                        }))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<TagEntity> findAllSorted(Integer limit, Integer offset) {
        String sql = "SELECT * FROM tb_tags ORDER BY name LIMIT ? OFFSET ?";
        return new HashSet<>(jdbcTemplate
                .query(sql, new TagRowMapper(), limit, offset));
    }
}
