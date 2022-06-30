package com.epam.business.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@ApiModel
@NoArgsConstructor
public class UpdateGiftCertificateRequest {

    @ApiModelProperty(value = "The unique name of the gift certificate",
            name = "name",
            dataType = "String",
            allowEmptyValue = true,
            position = 0)
    private String name;

    @PositiveOrZero
    @ApiModelProperty(value = "The price of the gift certificate",
            name = "price",
            dataType = "BigDecimal",
            allowableValues = "[0, infinity]",
            allowEmptyValue = true,
            position = 1)
    private BigDecimal price;

    @PositiveOrZero
    @ApiModelProperty(value = "Duration of the gift certificate in days",
            name = "duration",
            dataType = "Long",
            allowableValues = "[0, infinity]",
            allowEmptyValue = true,
            position = 2)
    private Long duration;

    @ApiModelProperty(value = "Description of the gift certificate",
            name = "description",
            dataType = "String",
            allowEmptyValue = true,
            position = 3)
    private String description;
}
