package com.epam.esm.dao;

import com.epam.esm.model.entity.TagEntity;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TagDao extends BaseDao<Long, TagEntity> {
    Set<TagEntity> saveAll(Set<TagEntity> tags);
    Set<TagEntity> findAllSorted(Integer limit, Integer offset);
}
