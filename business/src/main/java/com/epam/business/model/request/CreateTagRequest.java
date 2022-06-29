package com.epam.business.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 6/29/2022
 */
@Data
@NoArgsConstructor
public class CreateTagRequest {
    private String name;
}