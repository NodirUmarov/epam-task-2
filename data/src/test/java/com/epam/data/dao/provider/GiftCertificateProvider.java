package com.epam.data.dao.provider;

import com.epam.data.model.entity.GiftCertificateEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/24/2022
 */
public class GiftCertificateProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        List<Arguments> listOfArguments = new ArrayList<>();
        for (int i = 1; i <= 500; i++) {
            listOfArguments.add(Arguments.of(GiftCertificateEntity.builder()
                    .name("Certificate #" + i)
                    .price(BigDecimal.valueOf(i * 50))
                    .description("Description #" + i)
                    .duration(LocalDateTime.of(2022, 12, 11, 10, 9, 8))
                    .createDate(LocalDateTime.of(2022, 11, 10, 9, 8, 7))
                    .tags(TagProvider.entities())
                    .build()));
        }
        return listOfArguments.stream();
    }
}
