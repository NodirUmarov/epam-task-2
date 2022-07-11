package com.epam.data.dao.impl;

import com.epam.data.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.data.dao.GiftCertificateDao;
import com.epam.data.dao.impl.query.GiftCertificateQuery;
import com.epam.data.model.entity.GiftCertificateEntity;
import com.epam.data.model.entity.TagEntity;
import com.epam.lib.constants.SortType;
import com.epam.data.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.data.dao.impl.query.GiftCertificateQuery.INSERT;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.INSERT_JT;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.SELECT_BY_ID;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.UNTAG_CERTIFICATE;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.DELETE_BY_ID;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.DELETE_BY_ID_JT;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.SELECT_BY_NAME;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.SELECT_BY_NAME_ORD;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.SELECT_EXISTS;
import static com.epam.data.dao.impl.query.GiftCertificateQuery.UPDATE_BY_ID;


/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 */

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unchecked") // saveToJunctionTable method using generic  type array what causes unchecked assignment
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final GiftCertificateResultSetExtractor giftCertificateResultSetExtractor;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private <T> void checkForNull(T... object) {
        for (T t : object) {
            if (t == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    @Override
    public Optional<GiftCertificateEntity> findById(Long id) throws IllegalArgumentException {
        checkForNull(id);
        return Objects.requireNonNull(namedParameterJdbcTemplate.query(SELECT_BY_ID, new MapSqlParameterSource("id", id), giftCertificateResultSetExtractor))
                .stream()
                .findFirst();
    }

    @Override
    public GiftCertificateEntity getById(Long id) throws IllegalArgumentException, DataNotFoundException {
        checkForNull(id);
        try {
            return Objects.requireNonNull(namedParameterJdbcTemplate.query(SELECT_BY_ID,
                    new MapSqlParameterSource("id", id), giftCertificateResultSetExtractor)).get(0);
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            throw new DataNotFoundException();
        }
    }

    @Override
    @Transactional
    public GiftCertificateEntity save(GiftCertificateEntity giftCertificateEntity) throws IllegalArgumentException {
        checkForNull(giftCertificateEntity);
        if (giftCertificateEntity.getId() != null && existsById(giftCertificateEntity.getId())) {
            return update(giftCertificateEntity);
        } else {
            return saveNew(giftCertificateEntity);
        }
    }

    private GiftCertificateEntity saveNew(GiftCertificateEntity giftCertificateEntity) {
        KeyHolder holder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(INSERT, new MapSqlParameterSource()
                        .addValue("name", giftCertificateEntity.getName())
                        .addValue("price", giftCertificateEntity.getPrice())
                        .addValue("duration", giftCertificateEntity.getDuration())
                        .addValue("description", giftCertificateEntity.getDescription())
                        .addValue("createDate", giftCertificateEntity.getCreateDate()),
                holder,
                new String[]{"id" });
        giftCertificateEntity.setId(Objects.requireNonNull(holder.getKey()).longValue());
        saveToJunctionTable(giftCertificateEntity, giftCertificateEntity.getTags());

        return getById(giftCertificateEntity.getId());
    }

    private void saveToJunctionTable(GiftCertificateEntity giftCertificateEntity, Set<TagEntity> tagEntities) {
        Map<String, Object>[] batchInputs = new HashMap[tagEntities.size()];
        int index = 0;
        for (TagEntity tag : tagEntities) {
            Map<String, Object> params = new HashMap<>();
            params.put("certificateId", giftCertificateEntity.getId());
            params.put("name", tag.getName());
            batchInputs[index++] = params;
        }
        namedParameterJdbcTemplate.batchUpdate(INSERT_JT, batchInputs);
    }

    private GiftCertificateEntity update(GiftCertificateEntity giftCertificateEntity) {
        if (!existsById(giftCertificateEntity.getId())) {
            throw new DataNotFoundException();
        }
        namedParameterJdbcTemplate.update(UPDATE_BY_ID, new MapSqlParameterSource()
                .addValue("id", giftCertificateEntity.getId())
                .addValue("name", giftCertificateEntity.getName())
                .addValue("description", giftCertificateEntity.getDescription())
                .addValue("duration", giftCertificateEntity.getDuration())
                .addValue("price", giftCertificateEntity.getPrice())
                .addValue("updateDate", Timestamp.valueOf(LocalDateTime.now())));

        return getById(giftCertificateEntity.getId());
    }

    @Override
    public void deleteById(Long id) throws IllegalArgumentException {
        checkForNull(id);
        namedParameterJdbcTemplate.update(DELETE_BY_ID_JT, new MapSqlParameterSource("certificateId", id));
        namedParameterJdbcTemplate.update(DELETE_BY_ID, new MapSqlParameterSource("id", id));
    }

    @Override
    public Optional<GiftCertificateEntity> findByName(String name) throws IllegalArgumentException {
        checkForNull(name);
        return Objects.requireNonNull(namedParameterJdbcTemplate
                        .query(SELECT_BY_NAME, new MapSqlParameterSource("name", name), giftCertificateResultSetExtractor))
                .stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificateEntity> findByTag(String tag, Integer limit, Integer offset, SortType sortType) throws IllegalArgumentException {
        checkForNull(tag, limit, offset, sortType);
        GiftCertificateQuery.order = sortType.getValue();
        return namedParameterJdbcTemplate.query(SELECT_BY_NAME_ORD, new MapSqlParameterSource()
                        .addValue("tagName", tag)
                        .addValue("limit", limit)
                        .addValue("offset", offset),
                giftCertificateResultSetExtractor);
    }

    @Override
    public boolean existsById(Long id) throws IllegalArgumentException {
        checkForNull(id);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(SELECT_EXISTS,
                new MapSqlParameterSource("id", id), (rs, rowNum) -> rs.getBoolean(1)));
    }

    @Override
    public GiftCertificateEntity untagCertificate(Long id, Set<String> tags) throws IllegalArgumentException {
        checkForNull(id);
        tags.forEach(tag -> {
            checkForNull(tag);
            namedParameterJdbcTemplate.update(UNTAG_CERTIFICATE, new MapSqlParameterSource()
                    .addValue("certificateId", id)
                    .addValue("tagName", tag));
        });
        return getById(id);
    }
}
