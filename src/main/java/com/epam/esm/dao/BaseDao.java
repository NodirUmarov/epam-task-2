package com.epam.esm.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface BaseDao<ID, Entity> {
    Entity findById(ID id);
    Entity save(Entity entity);
    void delete(ID id);
}
