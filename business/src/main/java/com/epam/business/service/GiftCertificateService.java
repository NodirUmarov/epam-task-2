package com.epam.business.service;

import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.data.model.enums.SortType;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Service
public interface GiftCertificateService {
    GiftCertificateDto create(CreateGiftCertificateRequest request);

    GiftCertificateDto getByName(String name);

    Set<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType);

    void deleteById(Long id);

    GiftCertificateDto update(Long id, UpdateGiftCertificateRequest request);

}
