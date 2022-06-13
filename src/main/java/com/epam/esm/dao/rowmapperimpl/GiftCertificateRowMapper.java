package com.epam.esm.dao.rowmapperimpl;

import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntity> {

    @Override
    public GiftCertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificateResultSetExtractor extractor = new GiftCertificateResultSetExtractor();
        return extractor.extractData(rs);
    }

    private static class GiftCertificateResultSetExtractor implements ResultSetExtractor<GiftCertificateEntity> {

        @Override
        public GiftCertificateEntity extractData(ResultSet rs) throws SQLException, DataAccessException {
            Set<TagEntity> set = new HashSet<>();

            GiftCertificateEntity giftCertificateEntity = null;

            while (rs.next()) {

                if (giftCertificateEntity == null) {
                    Timestamp lastUpdated = rs.getTimestamp(7);
                    giftCertificateEntity = GiftCertificateEntity
                            .builder()
                            .id(rs.getLong(1))
                            .name(rs.getString(2))
                            .price(rs.getBigDecimal(3))
                            .duration(rs.getTimestamp(4).toLocalDateTime())
                            .description(rs.getString(5))
                            .createDate(rs.getTimestamp(6).toLocalDateTime())
                            .lastUpdateDate(lastUpdated != null ? lastUpdated.toLocalDateTime() : null)
                            .tags(set)
                            .build();
                }

                set.add(TagEntity
                        .builder()
                        .id(rs.getLong(8))
                        .name(rs.getString(9))
                        .build());
            }
            return giftCertificateEntity;
        }
    }
}
