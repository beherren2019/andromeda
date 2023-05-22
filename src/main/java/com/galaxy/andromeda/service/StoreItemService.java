package com.galaxy.andromeda.service;

import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.vo.store.StoreItemSearchListVO;
import com.galaxy.andromeda.vo.store.StoreItemVO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface StoreItemService {

    StoreItemVO getStoreItem(Long storeId, Long productId, Long storeItemId);

    List<StoreItemVO> getByStoreIdAndProductId(Long storeId, Long productId);

    List<StoreItemVO> getByStoreId(Long storeId);

    StoreItemVO addStoreItem(Long storeId, Long productId, StoreItemVO storeItemVO);

    void removeStoreItem(Long storeId, Long productId, Long storeItemId);

    StoreItemVO modifyStoreItems(Long storeId, Long productId, Long storeItemId, StoreItemVO storeItemVO);

    StoreItemSearchListVO getSearchedItems(int pageNo, int pageSize, Specification<StoreItem> specification);
}
