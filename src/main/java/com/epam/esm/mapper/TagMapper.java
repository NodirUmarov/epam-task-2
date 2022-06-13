package com.epam.esm.mapper;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper extends BaseMapper<TagEntity, TagDto> {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
}
