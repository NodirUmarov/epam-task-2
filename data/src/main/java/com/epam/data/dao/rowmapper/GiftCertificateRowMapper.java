package com.epam.data.dao.rowmapper;

import com.epam.data.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Implementation of {@link RowMapper} used by {@link JdbcTemplate} for mapping rows of a
 * {@link java.sql.ResultSet} on a per-row basis. Configured to map values of {@link GiftCertificateEntity} instance.
 * Performs the actual work of mapping each row to a result object, but don't need to worry about exception handling.
 * {@link java.sql.SQLException SQLExceptions} will be caught and handled
 * by the calling JdbcTemplate.
 *
 * <p>Typically used either for {@link JdbcTemplate}'s query methods
 * or for out parameters of stored procedures.
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 * @see GiftCertificateEntity
 * @see JdbcTemplate
 * @see RowMapper
 * @see RowCallbackHandler
 * @see GiftCertificateResultSetExtractor
 * @see ResultSetExtractor
 * @see org.springframework.jdbc.object.MappingSqlQuery
 * @since 0.1.0
 * @version 0.1.0
 */

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificateEntity> {

    /**
     * Implementation of {@link RowMapper#mapRow(ResultSet, int)} method, that maps each row of data
     * in the ResultSet. It is only supposed to map values of the current row.
     * @param rs the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting
     * column values (that is, there's no need to catch SQLException)
     *
     * @since 0.1.0
     */
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
