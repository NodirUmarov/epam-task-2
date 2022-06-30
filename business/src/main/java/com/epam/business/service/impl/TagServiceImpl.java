package com.epam.business.service.impl;

import com.epam.business.mapper.dto.TagMapper;
import com.epam.business.mapper.requestMapper.CreateTagMapper;
import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.CreateTagRequest;
import com.epam.business.service.TagService;
import com.epam.data.dao.TagDao;
import com.epam.data.model.entity.TagEntity;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;
    private final TagMapper tagMapper;
    private final CreateTagMapper createTagMapper;

    @Override
    public Set<TagDto> create(Set<CreateTagRequest> tags) {
        Set<TagEntity> tagEntitySet = tagDao.saveAll(createTagMapper.toEntitySet(tags));
        return tagMapper.toDtoSet(tagEntitySet);
    }

    @Override
    public Set<TagDto> getAllTags(Integer quantity, Integer page) {
        int offset = page - 1;

        return tagMapper.toDtoSet(tagDao.findAllSorted(quantity, offset));
    }

    @Override
    public void deleteById(Long id) {
        tagDao.deleteById(id);
    }
}
