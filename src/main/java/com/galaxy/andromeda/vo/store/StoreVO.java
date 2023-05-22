package com.galaxy.andromeda.vo.store;

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
public class StoreVO {

    private Long id;

    private String externalId;

    @NotNull(message = "Store name is mandatory!")
    @Size(max = 255, min = 3, message = "Store name must be between 3 and 255 chars!")
    private String name;

    @NotNull(message = "Store code is mandatory!")
    @Size(max = 24, min = 3, message = "Store code must be between 3 and 24 chars!")
    private String code;

    @NotNull(message = "Store street number is mandatory!")
    @Size(max = 8, min = 1, message = "Store street number must be between 1 and 8 chars!")
    private String streetNumber;

    @NotNull(message = "Store street name is mandatory!")
    @Size(max = 255, min = 3, message = "Store street name must be between 3 and 255 chars!")
    private String streetName;

    @NotNull(message = "Store zipcode is mandatory!")
    @Size(max = 16, min = 3, message = "Store zipcode must be between 3 and 16 chars!")
    private String zipcode;

    @NotNull(message = "Store city is mandatory!")
    @Size(max = 24, min = 2, message = "Store city must be between 2 and 24 chars!")
    private String city;

    @NotNull(message = "Store country is mandatory!")
    @Size(max = 36, min = 2, message = "Store country must be between 2 and 36 chars!")
    private String country;

}
