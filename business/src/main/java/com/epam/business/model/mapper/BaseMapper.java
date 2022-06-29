package com.epam.business.model.mapper;

import java.util.List;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
public interface BaseMapper<T, S> {
    T toEntity(S dto);
    S toDto(T entity);
    List<T> toEntityList(List<S> dtoList);
    List<S> toDtoList(List<T> entityList);
}
