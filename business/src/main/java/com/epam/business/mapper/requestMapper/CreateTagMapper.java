package com.epam.business.mapper.requestMapper;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.TagRequest;
import com.epam.data.model.entity.GiftCertificateEntity;
import com.epam.data.model.entity.TagEntity;
import org.mapstruct.Mapper;

/**
 * This interface inherits {@link EntityMapper} and sets to mapper parameters
 *
 * {@link TagEntity} to parameter T
 * {@link TagRequest} to parameter S
 *
 * All configs retrieved from {@link ConfigMapper}
 * The implementation of mapper will be created by mapstruct and will be passed to Spring as bean. Can be autowired.
 *
 * @see ConfigMapper
 * @see Mapper
 * @see EntityMapper
 * @see DtoMapper
 * @see TagEntity
 * @see TagRequest
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface CreateTagMapper extends EntityMapper<TagEntity, TagRequest> {
}
