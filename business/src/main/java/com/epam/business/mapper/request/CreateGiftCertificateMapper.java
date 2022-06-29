package com.epam.business.mapper.request;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.data.model.entity.GiftCertificateEntity;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface CreateGiftCertificateMapper extends EntityMapper<GiftCertificateEntity, CreateGiftCertificateRequest> {

    default LocalDateTime toLocalDateTime(Long duration) {
        return LocalDateTime.now().plusDays(duration);
    }
}
