package com.epam.esm.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGiftCertificateRequest {
    String name;
    BigDecimal price;
    LocalDate duration;
    String description;
    Set<String> tags;
}
