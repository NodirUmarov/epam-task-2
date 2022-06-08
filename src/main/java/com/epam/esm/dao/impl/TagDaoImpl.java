package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.rowmapperimpl.TagMapper;
import com.epam.esm.model.entity.TagEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDaoImpl implements TagDao {

    @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Optional<TagEntity> findById(Long id) {
        String sql = "SELECT * FROM tb_tags WHERE id IN (?);";
        return jdbcTemplate
                .query(sql, new TagMapper(), id)
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
}
