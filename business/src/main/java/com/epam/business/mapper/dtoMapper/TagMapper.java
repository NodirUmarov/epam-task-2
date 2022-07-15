package com.epam.business.mapper.dto;

import com.epam.business.mapper.config.DtoMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.mapper.config.ConfigMapper;
import com.epam.business.mapper.config.EntityMapper;
import com.epam.data.model.entity.TagEntity;
import org.mapstruct.Mapper;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Mapper(config = ConfigMapper.class)
public interface TagMapper extends EntityMapper<TagEntity, TagDto>, DtoMapper<TagEntity, TagDto> {
}
