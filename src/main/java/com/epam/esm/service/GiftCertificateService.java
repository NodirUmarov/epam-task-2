package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDto;
import com.epam.esm.model.request.CreateGiftCertificateRequest;
import org.springframework.stereotype.Service;

@Service
public interface GiftCertificateService {
    GiftCertificateDto create(CreateGiftCertificateRequest request);
}
