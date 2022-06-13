package com.epam.esm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GiftCertificateDto implements Serializable {
    Long id;
    String name;
    String description;
    BigDecimal price;
    Set<TagDto> tags;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    LocalDateTime duration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    LocalDateTime lastUpdateDate;
}
