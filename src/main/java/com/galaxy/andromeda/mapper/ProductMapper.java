package com.galaxy.andromeda.mapper;

import com.galaxy.andromeda.entity.Product;
import com.galaxy.andromeda.vo.store.ProductVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public List<ProductVO> mapProductsToProductsVO(List<Product> products) {
        if (products == null) {
            return null;
        }

        return products.stream().map(p -> mapProductToProductVO(p))
                .collect(Collectors.toList());
    }

    public ProductVO mapProductToProductVO(Product product) {
        if (product == null) {
            return null;
        }

        return ProductVO.builder()
                .externalId(product.getExternalId())
                .id(product.getId())
                .name(product.getName())
                .type(product.getType())
                .code(product.getCode())
                .build();
    }

    public Product mapProductVOToProduct(ProductVO productVO) {
        if (productVO == null) {
            return null;
        }

        return Product.builder()
                .externalId(productVO.getExternalId())
                .id(productVO.getId())
                .name(productVO.getName())
                .type(productVO.getType())
                .code(productVO.getCode())
                .build();
    }
}
