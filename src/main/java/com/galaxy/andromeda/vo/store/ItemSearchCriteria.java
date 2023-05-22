package com.galaxy.andromeda.vo.store;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSearchCriteria {

    private String itemName;

    private String storeName;

    private String storeExternalId;

    private String productName;

    private String productExternalId;

    private Long storeId;

    private Long productId;
}
