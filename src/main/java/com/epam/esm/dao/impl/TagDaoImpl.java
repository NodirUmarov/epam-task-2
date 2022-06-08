package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.config.DatabaseInitialization;
import com.epam.esm.model.entity.TagEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagDaoImpl implements TagDao {

    @NonNull JdbcTemplate jdbcTemplate;
    @NonNull DatabaseInitialization databaseInitialization;

    {
        databaseInitialization.createTable(TagEntity.class);
    }

    @Override
    public TagEntity findById(Long aLong) {
            String sql = "SELECT * FROM tb_giftcertificate WHERE id IN (?);";
            return jdbcTemplate.query(sql, (rs -> {
               return TagEntity
                       .builder()
                       .id(rs.getLong(1))
                       .name(rs.getString(2))
                       .build();
            }), aLong);
    }

    @Override
    public TagEntity save(TagEntity tagEntity) {
        return ;
    }

    @Override
    public void delete(Long aLong) {

    }
}
