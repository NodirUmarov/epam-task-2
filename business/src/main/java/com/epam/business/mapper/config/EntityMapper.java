package com.epam.business.mapper.config;

import java.util.Set;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
public interface EntityMapper<T, S> {
    T toEntity(S dto);

    Set<T> toEntitySet(Set<S> dtoSet);

}
