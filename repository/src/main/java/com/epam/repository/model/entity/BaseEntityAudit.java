package com.epam.repository.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class BaseEntityAudit<ID> extends BaseEntity<ID> {

    protected LocalDateTime createDate;
    protected LocalDateTime lastUpdateDate;

}
