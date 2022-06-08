package com.epam.esm.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftCertificateDto implements Serializable {
    Long id;
    String name;
    String description;
    BigDecimal price;
    LocalDate duration;
    LocalDateTime createDate;
    LocalDateTime lastUpdateDate;
}
