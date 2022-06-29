package com.epam.business.model.request;

import java.math.BigDecimal;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateGiftCertificateRequest {
    String name;
    BigDecimal price;
    Long duration;
    String description;
    Set<CreateTagRequest> tags;
}