package com.galaxy.andromeda.vo.store;

import com.galaxy.andromeda.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVO {

    private Long id;

    private String externalId;

    @NotNull(message = "Product name is mandatory!")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters")
    private String name;

    @NotNull(message = "Product code is mandatory!")
    @Size(min = 3, max = 24, message = "Product name must be between 3 and 24 characters")
    private String code;

    @NotNull(message = "Product type is mandatory!, any of VEG, NON_VEG, VEGAN")
    private ProductType type;

}
