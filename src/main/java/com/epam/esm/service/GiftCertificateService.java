package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.enums.SortType;
import com.epam.esm.model.request.CreateGiftCertificateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GiftCertificateService {
    GiftCertificateDto create(CreateGiftCertificateRequest request);

    GiftCertificateDto getByName(String name);

    List<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType);
}
