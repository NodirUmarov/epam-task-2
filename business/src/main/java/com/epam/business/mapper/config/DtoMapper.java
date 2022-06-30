package com.epam.business.mapper.config;

import java.util.Set;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
public interface DtoMapper<T, S> {
    S toDto(T entity);

    Set<S> toDtoSet(Set<T> entitySet);
}
