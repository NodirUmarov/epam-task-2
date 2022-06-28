package com.epam.repository.dao.extractor;

import com.epam.repository.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.repository.model.entity.GiftCertificateEntity;
import com.epam.repository.model.entity.TagEntity;
import lombok.NonNull;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of callback interface used by {@link JdbcTemplate}'s query methods.
 * It performs the actual work of extracting
 * results from a {@link java.sql.ResultSet}, but don't need to worry
 * about exception handling. {@link java.sql.SQLException SQLExceptions}
 * will be caught and handled by the calling JdbcTemplate.
 *
 * <p>This class is mainly used within the JDBC framework itself.
 * A {@link com.epam.repository.dao.rowmapper.GiftCertificateRowMapper} is usually a simpler choice for ResultSet processing,
 * mapping one result object per row instead of one result object for
 * the entire ResultSet.</p>
 *
 * <p>Configured to map values of {@link GiftCertificateEntity} instance.</p>
 *
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 * @since 0.1.0
 * @version 0.1.0
 * @see GiftCertificateEntity
 * @see JdbcTemplate
 * @see com.epam.repository.dao.rowmapper.GiftCertificateRowMapper
 * @see RowCallbackHandler
 * @see RowMapper
 * @see org.springframework.jdbc.core.support.AbstractLobStreamingResultSetExtractor
 * {@inheritDoc}
 */

@Component
public class GiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificateEntity>> {

    /**
     * Implementations to process the entire ResultSet.
     * @param rs the ResultSet to extract data from.
     * @return an arbitrary result object, or {@code null} if none.
     * @throws SQLException if an SQLException is encountered getting column
     * values or navigating (that is, there's no need to catch SQLException)
     * @throws DataAccessException in case of custom exceptions
     */
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
