package com.epam.esm.dao;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BaseDao<ID, Entity> {
    Optional<Entity> findById(ID id);
    Entity save(Entity entity);
    void delete(ID id);
}