package com.epam.business.model.mapper;

import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(componentModel = "spring")
public interface GiftCertificateMapper extends BaseMapper<GiftCertificateEntity, GiftCertificateDto> {
}
