package com.epam.esm.dao;

import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.enums.SortType;
import com.epam.esm.model.request.UpdateGiftCertificateRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateDao extends BaseDao<Long, GiftCertificateEntity> {
    Optional<GiftCertificateEntity> findByName(String name);

    List<GiftCertificateEntity> findByTag(String tag, Integer limit, Integer offset, SortType sortType);

    GiftCertificateEntity update(Long id, UpdateGiftCertificateRequest request);

    boolean existsById(Long id);

    void updateColumn(Long id, String columnName, String value);

}