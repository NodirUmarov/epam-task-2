package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.extractor.GiftCertificateResultSetExtractor;
import com.epam.esm.dao.rowmapper.GiftCertificateRowMapper;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.enums.SortType;
import com.epam.esm.model.request.CreateGiftCertificateRequest;
import com.epam.esm.model.request.UpdateGiftCertificateRequest;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Optional<GiftCertificateEntity> findById(Long id) {
        String sql = "" +
                "SELECT * FROM tb_gift_cetificates gc " +
                "JOIN gift_cetificate_has_tag gcht on gc.id = gcht.gift_cetificate_id " +
                "JOIN tb_tags t on gcht.tag_id = t.id " +
                "WHERE gc.id IN (?)";
        return jdbcTemplate
                .query(sql, new GiftCertificateResultSetExtractor(), id)
                .stream()
                .findFirst();
    }

    @Override
    @Transactional
    public GiftCertificateEntity save(GiftCertificateEntity giftCertificateEntity) {
        String sql = "" +
                "INSERT INTO tb_gift_cetificates(name, price, duration, description, create_date) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id;" +
                "";
        giftCertificateEntity.setCreateDate(LocalDateTime.now());
        jdbcTemplate.query(sql, rs -> {
                    giftCertificateEntity.setId(rs.getLong(1));
                },
                giftCertificateEntity.getName(),
                giftCertificateEntity.getPrice(),
                Timestamp.valueOf(giftCertificateEntity.getDuration()),
                giftCertificateEntity.getDescription(),
                Timestamp.valueOf(giftCertificateEntity.getCreateDate()));

        sql = "" +
                "INSERT INTO gift_cetificate_has_tag VALUES (?, ?);" +
                "";

        List<TagEntity> tags = new ArrayList<>(giftCertificateEntity.getTags());

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                TagEntity tagEntity = tags.get(i);
                ps.setLong(1, giftCertificateEntity.getId());
                ps.setLong(2, tagEntity.getId());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });

        return giftCertificateEntity;
    }

    @Override
    public void deleteById(Long id) {
        String cascadeDelete = "DELETE FROM gift_cetificate_has_tag WHERE gift_cetificate_id = ?";
        String sql = "DELETE FROM tb_gift_cetificates WHERE id = ?";
        jdbcTemplate.update(cascadeDelete, id);
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<GiftCertificateEntity> findByName(String name) {
        String sql = "" +
                "SELECT * FROM tb_gift_cetificates gc " +
                "JOIN gift_cetificate_has_tag gcht on gc.id = gcht.gift_cetificate_id " +
                "JOIN tb_tags t on gcht.tag_id = t.id " +
                "WHERE gc.name IN (?)";

        return jdbcTemplate
                .query(sql, new GiftCertificateResultSetExtractor(), name)
                .stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificateEntity> findByTag(String tag, Integer limit, Integer offset, SortType sortType) {
        String sql = "" +
                "SELECT * " +
                "FROM tb_gift_cetificates gc " +
                "JOIN gift_cetificate_has_tag gcht on gc.id = gcht.gift_cetificate_id " +
                "JOIN tb_tags t on gcht.tag_id = t.id " +
                "WHERE t.name IN (?) " +
                "ORDER BY (gc.name, t.name) " + (sortType.equals(SortType.NONE) ? "" : sortType + " ") +
                "LIMIT (?) " +
                "OFFSET (?);";
        return jdbcTemplate.query(sql,
                new GiftCertificateResultSetExtractor(),
                tag, limit, offset);
    }

    @Override
    public GiftCertificateEntity update(Long id, UpdateGiftCertificateRequest request) {
        String sql = "" +
                "UPDATE tb_gift_cetificates " +
                "SET ";

        if (request.getName() != null) {
            sql += "name = ?, ";
        }
        if (request.getDuration() != null) {
            sql += "duration = create_date + (duration - tb_gift_cetificates.create_date - INTERVAL '? DAY'), ";
        }
        if (request.getPrice() != null) {
            sql += "price = ?, ";
        }
        if (request.getDescription() != null) {
            sql += "description = ?, ";
        }

        sql += "last_update_date = NOW(), " +
                "WHERE id = ?;";

        int result = jdbcTemplate
                .update(sql,
                        request.getName(),
                        request.getPrice(),
                        request.getDescription(),
                        request.getDuration(),
                        id);
        if (result == 0) {
            throw new EntityNotFoundException("No entity found to update with id = " + id);
        }

        sql = "SELECT * FROM tb_gift_cetificates WHERE id = ?";
        return jdbcTemplate
                .query(sql, new GiftCertificateRowMapper(), id)
                .get(0);
    }

    public boolean existsById(Long id) {
        String sql = "SELECT EXISTS(SELECT * FROM tb_gift_cetificates WHERE id IN (?))";
        return jdbcTemplate.queryForObject(sql, Boolean.class, id);
    }

    public void updateColumn(Long id, String columnName, String value) {
        String sql = "UPDATE tb_gift_cetificates SET ? = ? WHERE id = ?";
        jdbcTemplate.update(sql, columnName, value, id);
    }
}
