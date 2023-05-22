package com.galaxy.andromeda.vo.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreItemVO {

    private Long id;

    private String externalId;

    @NotNull(message = "Item name is mandatory!")
    @Size(max = 255, min = 3, message = "Item name must be between 3 and 255 chars!")
    private String name;

    @NotNull(message = "Item productId is mandatory!")
    private Long productId;

    private String productExternalId;

    private String storeExternalId;

    @NotNull(message = "Item storeId is mandatory!")
    private Long storeId;

    @NotNull(message = "Item quantity is mandatory!")
    @Min(value = 1, message = "Item quantity must be greater than 0!")
    private int itemQuantity;
}
