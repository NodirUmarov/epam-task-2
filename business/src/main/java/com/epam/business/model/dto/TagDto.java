package com.epam.business.model.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDto implements Serializable {

    private final Long id;
    private final String name;
}
