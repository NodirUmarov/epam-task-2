package com.epam.esm.mapper;

import java.util.List;
import java.util.Set;

public interface BaseMapper<Entity, Dto> {
    Entity toEntity(Dto dto);

    Dto toDto(Entity entity);

    Set<Entity> toEntitySet(Set<Dto> dtoSet);

    Set<Dto> toDtoSet(Set<Entity> entitySet);

    List<Dto> toDtoList(List<Entity> entityList);
}
