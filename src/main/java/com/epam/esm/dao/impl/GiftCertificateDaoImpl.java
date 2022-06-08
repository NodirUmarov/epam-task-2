package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.config.DatabaseInitialization;
import com.epam.esm.model.entity.GiftCertificateEntity;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateDaoImpl implements GiftCertificateDao {

    @NonNull JdbcTemplate jdbcTemplate;
    @NonNull DatabaseInitialization databaseInitialization;

    {
        databaseInitialization.createTable(GiftCertificateEntity.class);
    }

    @Override
    public GiftCertificateEntity findById(Long aLong) {
        return null;
    }

    @Override
    public GiftCertificateEntity save(GiftCertificateEntity giftCertificateEntity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
