package com.epam.esm.dao.rowmapperimpl;

import com.epam.esm.model.entity.GiftCertificateEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GiftCertificateMapper implements RowMapper<GiftCertificateEntity> {

    @Override
    public GiftCertificateEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return GiftCertificateEntity
                .builder()
                .id(rs.getLong(1))
                .name(rs.getString(2))
                .price(rs.getBigDecimal(3))
                .duration(rs.getDate(4).toLocalDate())
                .description(rs.getString(5))
                .createDate(rs.getTimestamp(6).toLocalDateTime())
                .lastUpdateDate(rs.getTimestamp(7).toLocalDateTime())
                .build();
    }

}
