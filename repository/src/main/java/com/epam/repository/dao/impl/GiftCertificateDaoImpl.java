package com.epam.repository.dao.impl;

import com.epam.repository.dao.GiftCertificateDao;
import com.epam.repository.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.repository.model.entity.GiftCertificateEntity;
import com.epam.repository.model.entity.TagEntity;
import com.epam.repository.model.enums.SortType;
import com.epam.repository.model.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;


/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/16/2022
 */

@Repository
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    private final String SELECT_BY_ID = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "JOIN gift_certificate_has_tag gcht ON gc.id = gcht.gift_certificate_id " +
            "JOIN tb_tags t ON gcht.tag_id = t.id " +
            "WHERE gc.id IN (:id)";

    private final String INSERT_RETURN_ID = "" +
            "INSERT INTO tb_gift_certificates(name, price, duration, description, create_date) " +
            "VALUES (:name, :price, :duration, :description, :createDate) " +
            "RETURNING id;";

    private final String INSERT_JT = "" +
            "INSERT INTO gift_certificate_has_tag " +
            "VALUES (:certificateId, :tagId);";

    private final String DELETE_BY_ID_JT = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id IN :certificateId";

    private final String DELETE_BY_ID = "DELETE FROM tb_gift_certificates WHERE id IN :id";

    private final String SELECT_BY_NAME = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id " +
            "JOIN tb_tags t on gcht.tag_id = t.id " +
            "WHERE gc.name IN :name";

    private String order = SortType.NONE.getValue();
    private final String SELECT_BY_NAME_ORD = "" +
            "SELECT * FROM tb_gift_certificates gc " +
            "JOIN gift_certificate_has_tag gcht on gc.id = gcht.gift_certificate_id " +
            "JOIN tb_tags t on gcht.tag_id = t.id " +
            "WHERE t.name IN :tagName " +
            "ORDER BY (gc.name, t.name) " + order + " " +
            "LIMIT :limit " +
            "OFFSET :offset;";

    private final String SELECT_EXISTS = "SELECT EXISTS(SELECT * FROM tb_gift_certificates WHERE id IN (?))";

    private final String UPDATE_BY_ID = "" +
            "UPDATE tb_gift_certificates " +
            "SET name = (CASE WHEN name <> :name THEN :name ELSE name END), " +
            "description = (CASE WHEN description <> :description THEN :description ELSE description END), " +
            "duration = (CASE WHEN duration <> :duration THEN :duration ELSE duration END), " +
            "price = (CASE WHEN price <> :price THEN :price ELSE price END), " +
            "last_update_date = :updateDate " +
            "WHERE id = :id ";

    private final String UNTAG_CERTIFICATE = "" +
            "DELETE FROM gift_certificate_has_tag " +
            "WHERE tag_id IN (SELECT id FROM tb_tags t WHERE t.name IN (:tagName)) " +
            "AND gift_certificate_id IN (:certificateId)";

    private final GiftCertificateResultSetExtractor giftCertificateResultSetExtractor;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SafeVarargs
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
        return namedParameterJdbcTemplate.query(SELECT_BY_ID, new MapSqlParameterSource("id", id), giftCertificateResultSetExtractor)
                .stream()
                .findFirst();
    }

    @Override
    public GiftCertificateEntity getById(Long id) throws IllegalArgumentException, EntityNotFoundException {
        checkForNull(id);
        try {
            GiftCertificateEntity entity = namedParameterJdbcTemplate.query(SELECT_BY_ID,
                    new MapSqlParameterSource("id", id), giftCertificateResultSetExtractor).get(0);
            if (entity == null) {
                throw new NullPointerException();
            }
            return entity;
        } catch (NullPointerException ex) {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public GiftCertificateEntity save(GiftCertificateEntity giftCertificateEntity) throws IllegalArgumentException {
        checkForNull(giftCertificateEntity);
        if (existsById(giftCertificateEntity.getId())) {
            return update(giftCertificateEntity);
        } else {
            return saveNew(giftCertificateEntity);
        }
    }

    private GiftCertificateEntity saveNew(GiftCertificateEntity giftCertificateEntity) {
        giftCertificateEntity.setCreateDate(LocalDateTime.now());
        namedParameterJdbcTemplate.query(INSERT_RETURN_ID, new MapSqlParameterSource()
                        .addValue("name", giftCertificateEntity.getName())
                        .addValue("price", giftCertificateEntity.getPrice())
                        .addValue("duration", giftCertificateEntity.getDuration())
                        .addValue("description", giftCertificateEntity.getDescription())
                        .addValue("createDate", giftCertificateEntity.getCreateDate()),
                rs -> {
                    giftCertificateEntity.setId(rs.getLong(1));
                });

        saveToJunctionTable(giftCertificateEntity, giftCertificateEntity.getTags());

        return giftCertificateEntity;
    }

    private void saveToJunctionTable(GiftCertificateEntity giftCertificateEntity, Set<TagEntity> tagEntities) {
        Map<String, Object>[] batchInputs = new HashMap[tagEntities.size()];
        int index = 0;
        for (TagEntity tag : tagEntities) {
            Map<String, Object> params = new HashMap<>();
            params.put("certificateId", giftCertificateEntity.getId());
            params.put("tagId", tag.getId());
            batchInputs[index++] = params;
        }
        namedParameterJdbcTemplate.batchUpdate(INSERT_JT, batchInputs);
    }

    private GiftCertificateEntity update(GiftCertificateEntity giftCertificateEntity) {
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
        return namedParameterJdbcTemplate
                .query(SELECT_BY_NAME, new MapSqlParameterSource("name", name), giftCertificateResultSetExtractor)
                .stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificateEntity> findByTag(String tag, Integer limit, Integer offset, SortType sortType) throws IllegalArgumentException {
        checkForNull(tag, limit, offset, sortType);
        order = sortType.getValue();
        return namedParameterJdbcTemplate.query(SELECT_BY_NAME_ORD, new MapSqlParameterSource()
                        .addValue("tagName", tag)
                        .addValue("limit", limit)
                        .addValue("offset", offset),
                giftCertificateResultSetExtractor);
    }

    public boolean existsById(Long id) throws IllegalArgumentException {
        checkForNull(id);
        return Boolean.TRUE.equals(namedParameterJdbcTemplate.queryForObject(SELECT_EXISTS,
                new MapSqlParameterSource("id", id), (rs, rowNum) -> rs.getBoolean(0)));
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
