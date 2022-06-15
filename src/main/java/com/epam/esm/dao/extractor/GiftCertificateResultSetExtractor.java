package com.epam.esm.dao.extractor;

import com.epam.esm.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificateEntity>> {
    @Override
    public List<GiftCertificateEntity> extractData(@NonNull ResultSet rs) throws SQLException, DataAccessException {
        List<GiftCertificateEntity> entities = new ArrayList<>();
        GiftCertificateRowMapper mapper = new GiftCertificateRowMapper();

        long prevId = -1L;
        GiftCertificateEntity entity = null;
        Set<TagEntity> tags = null;

        while (rs.next()) {
            if (prevId != rs.getLong(1)) {
                prevId = rs.getLong(1);
                entity = mapper.mapRow(rs, 1);
                tags = new HashSet<>();
                entity.setTags(tags);
                entities.add(entity);
            }
            tags.add(TagEntity
                    .builder()
                            .id(rs.getLong(10))
                            .name(rs.getString(11))
                    .build());
        }

        return entities;
    }
}
