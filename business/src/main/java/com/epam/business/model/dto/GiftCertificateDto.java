package com.epam.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel
public class GiftCertificateDto implements Serializable {

    @ApiModelProperty(value = "The generated ID when saved in database",
            name = "ID",
            dataType = "Long",
            required = true,
            allowableValues = "[1, infinity]")
    private final Long id;

    @ApiModelProperty(value = "The unique name of the gift certificate",
            name = "name",
            dataType = "String",
            required = true,
            position = 1)
    private final String name;

    @ApiModelProperty(value = "Description of the gift certificate",
            name = "description",
            dataType = "String",
            allowEmptyValue = true,
            position = 2)
    private final String description;

    @ApiModelProperty(value = "The price of the gift certificate",
            name = "price",
            dataType = "BigDecimal",
            required = true,
            allowableValues = "[0, infinity]",
            position = 3)
    private final BigDecimal price;


    @ApiModelProperty(value = "Tags that gift certificate has",
            name = "tags",
            dataType = "Set",
            required = true,
            allowEmptyValue = true,
            position = 5)
    private final Set<TagDto> tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Duration of the gift certificate",
            name = "duration",
            dataType = "LocalDateTime",
            required = true,
            position = 4)
    private final LocalDateTime duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "createDate",
            dataType = "LocalDateTime",
            required = true,
            position = 6)
    private final LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "Date of gift certificate's creation",
            name = "lastUpdateDate",
            dataType = "LocalDateTime",
            position = 7)
    private final LocalDateTime lastUpdateDate;
}
