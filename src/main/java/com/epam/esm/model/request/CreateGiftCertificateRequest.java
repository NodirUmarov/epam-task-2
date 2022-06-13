package com.epam.esm.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGiftCertificateRequest {
    String name;
    BigDecimal price;
    Long duration;
    String description;
    Set<CreateTagRequest> tags;
}
