package com.epam.business.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftCertificateDto implements Serializable {

    private final Long id;
    private final LocalDateTime createDate;
    private final LocalDateTime lastUpdateDate;
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final LocalDateTime duration;
    private final Set<TagDto> tags;

}
