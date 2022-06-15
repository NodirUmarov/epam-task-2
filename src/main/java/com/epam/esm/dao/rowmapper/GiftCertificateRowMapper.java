package com.epam.esm.dao.rowmapper;

import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntity> {

    @Override
    public GiftCertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        Timestamp lastUpdated = rs.getTimestamp(7);
        return GiftCertificateEntity
                .builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .price(rs.getBigDecimal(3))
                .duration(rs.getTimestamp(4).toLocalDateTime())
                .description(rs.getString(5))
                .createDate(rs.getTimestamp(6).toLocalDateTime())
                .lastUpdateDate(lastUpdated != null ? lastUpdated.toLocalDateTime() : null)
                .build();
    }
}
