package com.galaxy.andromeda.mapper;

import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.vo.store.StoreItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreItemMapper {

    @Autowired
    ProductMapper productMapper;

    public List<StoreItemVO> mapItemsToItemsVO(List<StoreItem> storeItems) {
        if (storeItems == null) {
            return new ArrayList<>();
        }

        return storeItems.stream().map(si -> mapItemToItemVO(si)).collect(Collectors.toList());
    }

    public StoreItemVO mapItemToItemVO(StoreItem storeItem) {
        if (storeItem == null) {
            return null;
        }

        return StoreItemVO.builder()
                .productId(storeItem.getProduct().getId())
                .productExternalId(storeItem.getProduct().getExternalId())
                .storeId(storeItem.getStore().getId())
                .storeExternalId(storeItem.getStore().getExternalId())
                .id(storeItem.getId())
                .externalId(storeItem.getExternalId())
                .itemQuantity(storeItem.getItemQuantity())
                .name(storeItem.getName())
                .build();
    }

    public StoreItem mapItemVOToItem(StoreItemVO storeItemVO) {

        if (storeItemVO == null) {
            return null;
        }

        return StoreItem.builder()
                .name(storeItemVO.getName())
                .itemQuantity(storeItemVO.getItemQuantity())
                .build();
    }
}
