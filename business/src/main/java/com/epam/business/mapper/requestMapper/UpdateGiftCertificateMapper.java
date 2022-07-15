package com.epam.business.mapper.requestMapper;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.data.model.entity.GiftCertificateEntity;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

/**
 * This interface inherits {@link EntityMapper} and sets to mapper parameters
 *
 * {@link GiftCertificateEntity} to parameter T
 * {@link UpdateGiftCertificateRequest} to parameter S
 *
 * All configs retrieved from {@link ConfigMapper}
 * The implementation of mapper will be created by mapstruct and will be passed to Spring as bean. Can be autowired.
 *
 * @see ConfigMapper
 * @see Mapper
 * @see EntityMapper
 * @see DtoMapper
 * @see GiftCertificateEntity
 * @see UpdateGiftCertificateRequest
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface UpdateGiftCertificateMapper extends EntityMapper<GiftCertificateEntity, UpdateGiftCertificateRequest> {

    /**
     * This method is using internally by mapper, to convert duration in days to instance of {@link LocalDateTime}
     * @param duration
     * @return specified date from current time plus days passed as argument
     */
    default LocalDateTime toLocalDateTime(Long duration) {
        return LocalDateTime.now().plusDays(duration);
    }
}
