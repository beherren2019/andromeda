package com.galaxy.andromeda.mapper;

import com.galaxy.andromeda.entity.Store;
import com.galaxy.andromeda.vo.store.StoreVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreMapper {

    @Autowired
    StoreItemMapper storeItemMapper;

    public StoreVO mapStoreToStoreVO(Store store) {

        if (store == null) {
            return null;
        }

        return StoreVO.builder()
                .city(store.getCity())
                .country(store.getCountry())
                .externalId(store.getExternalId())
                .id(store.getId())
                .code(store.getCode())
                .name(store.getName())
                .streetName(store.getStreetName())
                .streetNumber(store.getStreetNumber())
                .zipcode(store.getZipcode())
                .build();
    }

    public Store mapStoreVoToStore(StoreVO storeVO) {

        if (storeVO == null) {
            return null;
        }

        return Store.builder()
                .city(storeVO.getCity())
                .country(storeVO.getCountry())
                .externalId(storeVO.getExternalId())
                .id(storeVO.getId())
                .code(storeVO.getCode())
                .name(storeVO.getName())
                .streetName(storeVO.getStreetName())
                .streetNumber(storeVO.getStreetNumber())
                .zipcode(storeVO.getZipcode())
                .build();
    }

    public List<StoreVO> mapStoresToStoresVO(List<Store> stores) {
        if (stores == null) {
            return new ArrayList<>();
        }

        return stores.stream().map(s -> mapStoreToStoreVO(s))
                .collect(Collectors.toList());
    }
}
