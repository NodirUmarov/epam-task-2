package com.epam.business.service.provider;

import com.epam.business.model.request.CreateGiftCertificateRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class CreateGiftCertificateProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        AtomicLong counter = new AtomicLong(1);
        return CreateTagProvider.entities().stream()
                .map(tags -> {
                    CreateGiftCertificateRequest request = new CreateGiftCertificateRequest();
                    request.setName("GiftCertificate #" + counter);
                    request.setDescription("Description #" + counter);
                    request.setPrice(BigDecimal.TEN.multiply(BigDecimal.valueOf(counter.get())));
                    request.setDuration(counter.getAndIncrement());
                    request.setTags(tags);
                    return Arguments.of(request);
                });
    }
}
