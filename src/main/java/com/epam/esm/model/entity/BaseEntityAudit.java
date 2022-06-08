package com.epam.esm.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntityAudit<ID> extends BaseEntity<ID> {

    LocalDateTime createDate;
    LocalDateTime lastUpdateDate;

}
