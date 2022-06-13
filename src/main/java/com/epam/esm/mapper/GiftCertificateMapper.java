package com.epam.esm.mapper;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GiftCertificateMapper extends BaseMapper<GiftCertificateEntity, GiftCertificateDto> {
    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);
}