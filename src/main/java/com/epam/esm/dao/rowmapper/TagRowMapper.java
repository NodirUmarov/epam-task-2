package com.epam.esm.dao.rowmapper;

import com.epam.esm.model.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TagRowMapper implements RowMapper<TagEntity> {

    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return TagEntity
                .builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .build();
    }
}