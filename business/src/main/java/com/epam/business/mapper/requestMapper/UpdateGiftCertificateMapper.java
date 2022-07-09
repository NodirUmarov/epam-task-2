package com.epam.business.mapper.requestMapper;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(config = ConfigMapper.class)
public interface UpdateGiftCertificateMapper extends EntityMapper<GiftCertificateEntity, UpdateGiftCertificateRequest> {
    default LocalDateTime toLocalDateTime(Long duration) {
        return LocalDateTime.now().plusDays(duration);
    }
}
