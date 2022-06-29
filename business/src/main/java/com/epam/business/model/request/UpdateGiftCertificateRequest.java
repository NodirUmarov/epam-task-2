package com.epam.business.model.request;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@NoArgsConstructor
public class UpdateGiftCertificateRequest {

    private String name;
    private BigDecimal price;
    private Long duration;
    private String description;
}
