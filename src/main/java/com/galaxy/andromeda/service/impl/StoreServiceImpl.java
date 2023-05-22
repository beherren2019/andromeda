package com.galaxy.andromeda.service.impl;

import com.galaxy.andromeda.entity.Store;
import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.exception.EntityNotAvailableException;
import com.galaxy.andromeda.exception.InputValidationException;
import com.galaxy.andromeda.exception.IntegrityViolationException;
import com.galaxy.andromeda.mapper.StoreMapper;
import com.galaxy.andromeda.repository.StoreItemRepository;
import com.galaxy.andromeda.repository.StoreRepository;
import com.galaxy.andromeda.service.StoreService;
import com.galaxy.andromeda.util.AndromedaUtil;
import com.galaxy.andromeda.vo.store.StoreVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.galaxy.andromeda.util.AndromedaUtil.pickValue;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreItemRepository storeItemRepository;

    @Autowired
    StoreMapper storeMapper;

    @Override
    public StoreVO getStore(Long id) {

        Store store = storeRepository.findById(id).orElseThrow(
                () ->  new EntityNotAvailableException("Entity for the id: " + id + " is not available")
        );

        StoreVO storeVO = storeMapper.mapStoreToStoreVO(store);

        return storeVO;
    }

    @Override
    public List<StoreVO> getAllStores() {
        List<Store> stores = storeRepository.findAll();

        return storeMapper.mapStoresToStoresVO(stores);
    }

    @Override
    public StoreVO saveStore(StoreVO storeVO) {

        if (storeVO == null) {
            throw new InputValidationException("Store cannot be null to create");
        }

        if (StringUtils.isEmpty(storeVO.getName())) {
            throw new InputValidationException("Store name is mandatory create a store");
        }

        if (StringUtils.isEmpty(storeVO.getStreetName())) {
            throw new InputValidationException("Store street name is mandatory create a store");
        }

        if (StringUtils.isEmpty(storeVO.getStreetNumber())) {
            throw new InputValidationException("Store street number is mandatory create a store");
        }

        if (StringUtils.isEmpty(storeVO.getCountry())) {
            throw new InputValidationException("Store country is mandatory create a store");
        }

        if (StringUtils.isEmpty(storeVO.getCity())) {
            throw new InputValidationException("Store city is mandatory create a store");
        }

        if (StringUtils.isEmpty(storeVO.getZipcode())) {
            throw new InputValidationException("Store zipcode is mandatory create a store");
        }

        storeVO.setExternalId(AndromedaUtil.randomUUID());

        Store store = storeMapper.mapStoreVoToStore(storeVO);
        store.setCreatedBy("ADMIN");
        store.setCreatedOn(AndromedaUtil.getCurrentDateTime());

        return storeMapper.mapStoreToStoreVO(storeRepository.save(store));
    }

    @Override
    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(
                () ->  new EntityNotAvailableException("Entity for the id: " + id + " is not available")
        );

        List<StoreItem> storeItems = storeItemRepository.findStoreItemsByStoreId(id);

        if (storeItems != null && !storeItems.isEmpty() ) {
            throw new IntegrityViolationException(HttpStatus.BAD_REQUEST, "Store cannot be deleted as it assigned with items");
        }

        storeRepository.delete(store);
    }

    @Override
    public StoreVO editStore(Long id, StoreVO storeVO) {

        Store store = storeRepository.findById(id).orElseThrow(
                () ->  new EntityNotAvailableException("Entity for the id: " + id + " is not available")
        );

        store.setName(pickValue(storeVO.getName(), store.getName()));
        store.setStreetName(pickValue(storeVO.getStreetName(), store.getStreetName()));
        store.setStreetNumber(pickValue(storeVO.getStreetNumber(), store.getStreetNumber()));
        store.setZipcode(pickValue(storeVO.getZipcode(), store.getZipcode()));
        store.setCity(pickValue(storeVO.getCity(), store.getCity()));
        store.setCountry(pickValue(storeVO.getCountry(), store.getCountry()));

        return storeMapper.mapStoreToStoreVO(storeRepository.save(store));
    }

}
