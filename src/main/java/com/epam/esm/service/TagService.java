package com.epam.esm.service;

import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.request.CreateTagRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface TagService {
    Set<TagDto> create(Set<CreateTagRequest> tags);

    Set<TagDto> getAllTags(Integer quantity, Integer page);
}
