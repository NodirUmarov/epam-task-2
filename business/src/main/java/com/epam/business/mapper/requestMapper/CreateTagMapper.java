package com.epam.business.mapper.requestMapper;

import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.business.model.request.TagRequest;
import com.epam.data.model.entity.TagEntity;
import org.mapstruct.Mapper;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Mapper(config = ConfigMapper.class)
public interface CreateTagMapper extends EntityMapper<TagEntity, TagRequest> {
}
