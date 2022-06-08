package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.rowmapperimpl.GiftCertificateMapper;
import com.epam.esm.model.entity.GiftCertificateEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @NonNull JdbcTemplate jdbcTemplate;

    @Override
    public Optional<GiftCertificateEntity> findById(Long id) {
        String sql = "SELECT * FROM tb_gift_cetificates WHERE id IN (?);";
        return jdbcTemplate
                .query(sql, new GiftCertificateMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public GiftCertificateEntity save(GiftCertificateEntity giftCertificateEntity) {
        String sql = "" +
                "INSERT INTO tb_gift_cetificates(name, price, duration, description, create_date) " +
                "VALUES (?) RETURNING id;" +
                "";
        giftCertificateEntity.setCreateDate(LocalDateTime.now());
        jdbcTemplate.query(sql, rs -> {
            giftCertificateEntity.setId(rs.getLong(1));
        },
                giftCertificateEntity.getName(),
                giftCertificateEntity.getPrice(),
                giftCertificateEntity.getDuration(),
                giftCertificateEntity.getDescription(),
                giftCertificateEntity.getCreateDate());
        return giftCertificateEntity;
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tb_gift_cetificates WHERE id = ?";
        jdbcTemplate.update(sql);
    }
}
