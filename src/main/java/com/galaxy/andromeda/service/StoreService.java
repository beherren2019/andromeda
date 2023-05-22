package com.galaxy.andromeda.service;

import com.galaxy.andromeda.vo.store.StoreVO;

import java.util.List;

public interface StoreService {

    StoreVO getStore(Long id);

    StoreVO saveStore(StoreVO storeVO);

    void deleteStore(Long id);

    StoreVO editStore(Long id, StoreVO storeVO);

    List<StoreVO> getAllStores();
}
