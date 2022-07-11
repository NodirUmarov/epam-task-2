package com.epam.data.dao.provider;

import com.epam.data.model.entity.GiftCertificateEntity;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class GiftCertificateProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return LongStream.range(1, 51).mapToObj(i -> Arguments.of(GiftCertificateEntity.builder()
                .name("Certificate #" + i)
                .price(BigDecimal.valueOf(i * 50))
                .description("Description #" + i)
                .duration(LocalDateTime.of(2022, 12, 11, 10, 9, 8))
                .createDate(LocalDateTime.of(2022, 11, 10, 9, 8, 7))
                .tags(TagProvider.entities())
                .build()));
    }
}
