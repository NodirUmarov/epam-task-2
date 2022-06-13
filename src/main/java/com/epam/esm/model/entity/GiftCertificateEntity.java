package com.epam.esm.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateEntity extends BaseEntityAudit<Long> {

    String name;
    String description;
    BigDecimal price;
    LocalDateTime duration;
    Set<TagEntity> tags;

}
