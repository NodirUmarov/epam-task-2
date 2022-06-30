package com.epam.business.service;

import com.epam.business.model.dto.TagDto;
import com.epam.business.model.request.CreateTagRequest;
import java.util.Set;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/30/2022
 */
public interface TagService {
    Set<TagDto> create(Set<CreateTagRequest> tags);

    Set<TagDto> getAllTags(Integer quantity, Integer page);

    void deleteById(Long id);
}