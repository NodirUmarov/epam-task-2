package com.epam.esm.model.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateEntity extends BaseEntity<Long> {

    String name;
    String description;
    String price;
    LocalDate duration;

}
