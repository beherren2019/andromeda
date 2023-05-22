package com.galaxy.andromeda.repository;

import com.galaxy.andromeda.entity.StoreItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItem, Long>, JpaSpecificationExecutor<StoreItem> {

    List<StoreItem> findStoreItemsByStoreIdAndProductId(Long storeId, Long productId);

    List<StoreItem> findStoreItemsByStoreId(Long storeId);

    List<StoreItem> findStoreItemsByProductId(Long productId);

    Optional<StoreItem> findStoreItemByIdAndStoreIdAndProductId(Long itemId, Long storeId, Long productId);

    Page<StoreItem> findAll(Specification<StoreItem> spec, Pageable pageable);

}
