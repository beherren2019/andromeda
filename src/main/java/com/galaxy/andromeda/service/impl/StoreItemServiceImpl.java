package com.galaxy.andromeda.service.impl;

import com.galaxy.andromeda.entity.Product;
import com.galaxy.andromeda.entity.Store;
import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.exception.EntityNotAvailableException;
import com.galaxy.andromeda.exception.InputValidationException;
import com.galaxy.andromeda.mapper.StoreItemMapper;
import com.galaxy.andromeda.repository.ProductRepository;
import com.galaxy.andromeda.repository.StoreItemRepository;
import com.galaxy.andromeda.repository.StoreRepository;
import com.galaxy.andromeda.service.StoreItemService;
import com.galaxy.andromeda.util.AndromedaUtil;
import com.galaxy.andromeda.vo.store.StoreItemSearchListVO;
import com.galaxy.andromeda.vo.store.StoreItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.galaxy.andromeda.util.AndromedaUtil.pickValue;

@Service
public class StoreItemServiceImpl implements StoreItemService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreItemRepository storeItemRepository;

    @Autowired
    StoreItemMapper storeItemMapper;

    @Override
    public StoreItemVO getStoreItem(Long storeId, Long productId, Long storeItemId) {

        checkStoreAndProductAvailability(storeId, productId);

        StoreItem storeItem = storeItemRepository
                .findStoreItemByIdAndStoreIdAndProductId(storeItemId, storeId, productId)
                .orElseThrow(() -> new EntityNotAvailableException("StoreItem for the storeId: " + storeId +
                        " productId: " + productId +
                        " id: " + storeItemId + " is not available"));

        return storeItemMapper.mapItemToItemVO(storeItem);
    }

    @Override
    public List<StoreItemVO> getByStoreIdAndProductId(Long storeId, Long productId) {

        checkStoreAndProductAvailability(storeId, productId);

        List<StoreItem> storeItems = storeItemRepository.findStoreItemsByStoreIdAndProductId(storeId, productId);

        return storeItemMapper.mapItemsToItemsVO(storeItems);
    }

    @Override
    public List<StoreItemVO> getByStoreId(Long storeId) {

        checkStoreAvailability(storeId);

        List<StoreItem> storeItem = storeItemRepository.findStoreItemsByStoreId(storeId);

        return storeItemMapper.mapItemsToItemsVO(storeItem);
    }

    @Override
    public StoreItemVO addStoreItem(Long storeId, Long productId, StoreItemVO storeItemVO) {

        Store store = checkStoreAvailability(storeId);
        Product product = checkProductAvailability(productId);

        if (storeItemVO.getItemQuantity() <= 0) {
            throw new InputValidationException("Item quantity should have positive number");
        }

        StoreItem storeItem = storeItemMapper.mapItemVOToItem(storeItemVO);

        storeItem.setStore(store);
        storeItem.setProduct(product);
        storeItem.setExternalId(AndromedaUtil.randomUUID());
        storeItem.setCreatedBy("ADMIN");
        storeItem.setCreatedOn(AndromedaUtil.getCurrentDateTime());

        return storeItemMapper.mapItemToItemVO(storeItemRepository.save(storeItem));
    }

    @Override
    public void removeStoreItem(Long storeId, Long productId, Long storeItemId) {
        checkStoreAndProductAvailability(storeId, productId);

        StoreItem storeItem = storeItemRepository.findById(storeItemId)
                .orElseThrow(() -> new EntityNotAvailableException("StoreItem for the id: " + storeItemId + " is not available"));

        validateStoreAndProductIds(storeId, productId, storeItem);

        storeItemRepository.delete(storeItem);
    }


    @Override
    public StoreItemVO modifyStoreItems(Long storeId, Long productId, Long storeItemId, StoreItemVO storeItemVO) {
        checkStoreAndProductAvailability(storeId, productId);

        StoreItem storeItem = storeItemRepository.findById(storeItemId)
                .orElseThrow(() -> new EntityNotAvailableException("StoreItem for the id: " + storeItemId + " is not available"));

        validateStoreAndProductIds(storeId, productId, storeItem);

        if (storeItemVO.getItemQuantity() < 0) {
            throw new InputValidationException("Item quantity should have positive number");
        }
        storeItem.setName(pickValue(storeItemVO.getName(), storeItem.getName()));
        storeItem.setItemQuantity(pickValue(storeItemVO.getItemQuantity(), storeItem.getItemQuantity()));

        return storeItemMapper.mapItemToItemVO(storeItemRepository.save(storeItem));

    }

    @Override
    public StoreItemSearchListVO getSearchedItems(int pageNo, int pageSize, Specification<StoreItem> specification) {

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<StoreItem> pages = storeItemRepository.findAll(specification, pageable);

        if (pages != null && pages.getContent() != null) {
            List<StoreItem> storeItems = pages.getContent();
            if (storeItems != null && storeItems.size() > 0) {
                StoreItemSearchListVO respList = StoreItemSearchListVO.builder()
                        .pageNo(pages.getNumber() + 1)
                        .totalCount(pages.getTotalElements())
                        .totalPages(pages.getTotalPages())
                        .storeItems(storeItemMapper.mapItemsToItemsVO(storeItems))
                        .build();
                return respList;
            }
        }
        return StoreItemSearchListVO.builder()
                .totalCount(0)
                .totalPages(0)
                .build();
    }

    private void checkStoreAndProductAvailability(Long storeId, Long productId) {

        checkStoreAvailability(storeId);
        checkProductAvailability(productId);
    }

    private Store checkStoreAvailability(Long storeId) {

       return storeRepository.findById(storeId)
                .orElseThrow(() -> new EntityNotAvailableException("Store for the id: " + storeId + " is not available"));

    }

    private Product checkProductAvailability(Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotAvailableException("Product for the id: " + productId + " is not available"));
    }

    private void validateStoreAndProductIds(Long storeId, Long productId, StoreItem storeItem) {

        if (!storeItem.getStore().getId().equals(storeId)) {
            throw new InputValidationException("Item mismatch with store");
        }

        if (!storeItem.getProduct().getId().equals(productId)) {
            throw new InputValidationException("Item mismatch with product");
        }
    }
}
