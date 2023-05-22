package com.galaxy.andromeda.spec;

import com.galaxy.andromeda.entity.Product;
import com.galaxy.andromeda.entity.Store;
import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.util.AndromedaConstants;
import com.galaxy.andromeda.vo.store.ItemSearchCriteria;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class ItemSpecification {

    public Specification<StoreItem> getSearchSpecification(String sortBy, String sortDirection, ItemSearchCriteria itemSearchCriteria) {
        return (root, query, criteriaBuilder) -> {

            Join<Store, StoreItem> storeItemJoin = root.join("store");
            Join<Product, StoreItem> productItemJoin = root.join("product");

            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(itemSearchCriteria.getItemName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + itemSearchCriteria.getItemName().toLowerCase() + "%"));
            }

            if (itemSearchCriteria.getStoreId() != null) {
                predicates.add(criteriaBuilder.equal(storeItemJoin.get("id"), itemSearchCriteria.getStoreId()));
            }

            if (!StringUtils.isEmpty(itemSearchCriteria.getStoreExternalId())) {
                predicates.add(criteriaBuilder.equal(storeItemJoin.get("externalId"), itemSearchCriteria.getStoreExternalId()));
            }

            if (!StringUtils.isEmpty(itemSearchCriteria.getStoreName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(storeItemJoin.get("name")),
                        "%" + itemSearchCriteria.getStoreName().toLowerCase() + "%"));
            }

            if (itemSearchCriteria.getProductId() != null) {
                predicates.add(criteriaBuilder.equal(productItemJoin.get("id"), itemSearchCriteria.getProductId()));
            }

            if (!StringUtils.isEmpty(itemSearchCriteria.getProductExternalId())) {
                predicates.add(criteriaBuilder.equal(productItemJoin.get("externalId"), itemSearchCriteria.getProductExternalId()));
            }

            if (!StringUtils.isEmpty(itemSearchCriteria.getProductName())) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(productItemJoin.get("name")),
                        "%" + itemSearchCriteria.getProductName().toLowerCase() + "%"));
            }

            if (sortDirection.equals(AndromedaConstants.DEFAULT_SORT_DIRECTION)) {
                query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
