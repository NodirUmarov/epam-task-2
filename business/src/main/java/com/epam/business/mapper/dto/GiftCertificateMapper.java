package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface GiftCertificateMapper extends EntityMapper<GiftCertificateEntity, GiftCertificateDto>,
        DtoMapper<GiftCertificateEntity, GiftCertificateDto> {
}