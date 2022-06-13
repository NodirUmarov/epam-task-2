package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.TagEntity;
import com.epam.esm.model.request.CreateTagRequest;
import com.epam.esm.service.TagService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.mapper.TagMapper.INSTANCE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TagServiceImpl implements TagService {

    @NonNull TagDao tagDao;

    @Override
    public Set<TagDto> create(Set<CreateTagRequest> tags) {
        return INSTANCE
                .toDtoSet(tagDao
                        .saveAll(tags
                                .stream()
                                .map(tag -> new TagEntity(tag.getName()))
                                .collect(Collectors.toSet())));
    }

    @Override
    public Set<TagDto> getAllTags(Integer quantity, Integer page) {
        return INSTANCE.toDtoSet(tagDao.findAllSorted(quantity, page - 1));
    }

}
