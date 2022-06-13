package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.dto.TagDto;
import com.epam.esm.model.entity.GiftCertificateEntity;
import com.epam.esm.model.enums.SortType;
import com.epam.esm.model.request.CreateGiftCertificateRequest;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static com.epam.esm.mapper.GiftCertificateMapper.INSTANCE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    @NonNull GiftCertificateDao giftCertificateDao;
    @NonNull TagService tagService;

    @Override
    public GiftCertificateDto create(CreateGiftCertificateRequest request) {
        Set<TagDto> tagDtoSet = tagService.create(request.getTags());

        return GiftCertificateMapper.INSTANCE
                .toDto(giftCertificateDao
                        .save(GiftCertificateEntity
                                .builder()
                                .tags(TagMapper.INSTANCE.toEntitySet(tagDtoSet))
                                .name(request.getName())
                                .price(request.getPrice())
                                .duration(LocalDateTime.now().plusDays(request.getDuration()))
                                .description(request.getDescription())
                                .build()));
    }

    @Override
    public GiftCertificateDto getByName(String name) {
        return INSTANCE
                .toDto(giftCertificateDao
                        .findByName(name)
                        .orElseThrow(() ->
                                new EntityNotFoundException("GiftCertificate with name '" + name + "' not found in db")));
    }

    @Override
    public List<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType) {
        return INSTANCE
                .toDtoList(giftCertificateDao
                        .findByTag(tag, quantity, page - 1, sortType));
    }
}
