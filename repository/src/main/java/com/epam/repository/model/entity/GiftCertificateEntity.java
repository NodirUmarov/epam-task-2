package com.epam.repository.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateEntity extends BaseEntityAudit<Long> {

    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime duration;
    private Set<TagEntity> tags;

}
