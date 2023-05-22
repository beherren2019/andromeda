package com.galaxy.andromeda.controller;

import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.service.StoreItemService;
import com.galaxy.andromeda.spec.ItemSpecification;
import com.galaxy.andromeda.util.AndromedaConstants;
import com.galaxy.andromeda.vo.store.ItemSearchCriteria;
import com.galaxy.andromeda.vo.store.StoreItemSearchListVO;
import com.galaxy.andromeda.vo.store.StoreItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Slf4j
@RestController
@RequestMapping("")
@Validated
public class StoreItemController {

    @Autowired
    StoreItemService storeItemService;

    @Autowired
    ItemSpecification itemSpecification;

    @GetMapping(value = "/items", produces = "application/json")
    public StoreItemSearchListVO getSearchItems(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "storeId", required = false) Long storeId,
            @RequestParam(value = "storeExternalId", required = false) String storeExternalId,
            @RequestParam(value = "storeName", required = false) String storeName,
            @RequestParam(value = "productId", required = false) Long productId,
            @RequestParam(value = "productExternalId", required = false) String productExternalId,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pageNo", defaultValue = AndromedaConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AndromedaConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AndromedaConstants.DIRECTION_CREATE_ON, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AndromedaConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) {

        log.info("StoreItemController::getSearchItems::Start");

        ItemSearchCriteria itemSearchCriteria = ItemSearchCriteria.builder()
                .itemName(name)
                .storeName(storeName)
                .storeId(storeId)
                .storeExternalId(storeExternalId)
                .productName(productName)
                .productId(productId)
                .productExternalId(productExternalId)
                .build();

        Specification<StoreItem> specification = itemSpecification.getSearchSpecification(sortBy, sortDirection, itemSearchCriteria);

        StoreItemSearchListVO storeItemSearchList = storeItemService.getSearchedItems(pageNo, pageSize, specification);

        log.info("StoreItemController::getSearchItems::End");
        return storeItemSearchList;
    }

    @PostMapping(value = "/stores/{storeId}/products/{productId}/items", consumes = "application/json", produces = "application/json")
    public StoreItemVO addStoreItem(@NotNull @PathVariable Long storeId,
                                    @NotNull @PathVariable Long productId,
                                    @RequestBody @Valid StoreItemVO storeItemVO) {

        log.info("StoreItemController::addStoreItem::Start");
        StoreItemVO storeItem = storeItemService.addStoreItem(storeId, productId, storeItemVO);
        log.info("StoreItemController::addStoreItem::End");
        return storeItem;
    }

    @DeleteMapping(value = "/stores/{storeId}/products/{productId}/items/{storeItemId}", produces = "application/json")
    public void removeStoreItem(@NotNull @PathVariable Long storeId,
                                @NotNull @PathVariable Long productId,
                                @NotNull @PathVariable Long storeItemId) {

        log.info("StoreItemController::removeStoreItem::Start");
        storeItemService.removeStoreItem(storeId, productId, storeItemId);
        log.info("StoreItemController::removeStoreItem::End");
    }

    @PatchMapping(value = "/stores/{storeId}/products/{productId}/items/{storeItemId}", consumes = "application/json", produces = "application/json")
    public StoreItemVO editStoreItem(@NotNull @PathVariable Long storeId,
                                     @NotNull @PathVariable Long productId,
                                     @NotNull @PathVariable Long storeItemId,
                                     @NotNull @RequestBody StoreItemVO storeItemVO) {

        log.info("StoreItemController::editStoreItem::Start");

        StoreItemVO result = storeItemService.modifyStoreItems(storeId, productId, storeItemId, storeItemVO);

        log.info("StoreItemController::editStoreItem::End");

        return result;
    }
}
