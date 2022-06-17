package com.epam.repository.model.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity extends BaseEntity<Long> {
    private String name;
}
