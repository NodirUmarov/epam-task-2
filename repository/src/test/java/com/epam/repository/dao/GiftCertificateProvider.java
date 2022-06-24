package com.epam.repository.dao;

import com.epam.repository.model.entity.GiftCertificateEntity;
import com.epam.repository.model.entity.TagEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class GiftCertificateProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        List<Arguments> listOfArguments = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            listOfArguments.add(Arguments.of(GiftCertificateEntity.builder()
                    .name("Certificate #" + i)
                    .price(BigDecimal.valueOf(i * 50))
                    .description("Description #" + i)
                    .duration(LocalDateTime.now().plusNanos(i * 1000))
                    .tags(new HashSet<>(TagEntityProvider.entities()))
                    .build()));
        }
        return listOfArguments.stream();
    }
}
