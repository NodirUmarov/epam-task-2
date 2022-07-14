package com.epam.business.service;

import com.epam.business.exception.EntityIdNotFoundException;
import com.epam.business.exception.EntityNameNotFoundException;
import com.epam.business.model.dto.GiftCertificateDto;
import com.epam.business.model.request.CreateGiftCertificateRequest;
import com.epam.business.model.request.UpdateGiftCertificateRequest;
import com.epam.lib.constants.SortType;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Service
public interface GiftCertificateService {
    GiftCertificateDto create(CreateGiftCertificateRequest request);

    GiftCertificateDto getById(Long id);

    GiftCertificateDto getByName(String name) throws EntityNameNotFoundException;

    Set<GiftCertificateDto> getByTag(String tag, Integer quantity, Integer page, SortType sortType);

    void deleteById(Long id);

    GiftCertificateDto updateById(Long id, UpdateGiftCertificateRequest request) throws EntityIdNotFoundException;

    GiftCertificateDto untag(Long id, Set<String> tags);

}
