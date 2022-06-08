package com.epam.esm.dao;

import com.epam.esm.model.entity.TagEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface TagDao extends BaseDao<Long, TagEntity> {
}
