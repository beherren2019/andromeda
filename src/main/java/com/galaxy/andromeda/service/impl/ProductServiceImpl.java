package com.galaxy.andromeda.service.impl;

import com.galaxy.andromeda.entity.Product;
import com.galaxy.andromeda.entity.StoreItem;
import com.galaxy.andromeda.exception.EntityNotAvailableException;
import com.galaxy.andromeda.exception.InputValidationException;
import com.galaxy.andromeda.exception.IntegrityViolationException;
import com.galaxy.andromeda.mapper.ProductMapper;
import com.galaxy.andromeda.repository.ProductRepository;
import com.galaxy.andromeda.repository.StoreItemRepository;
import com.galaxy.andromeda.service.ProductService;
import com.galaxy.andromeda.util.AndromedaUtil;
import com.galaxy.andromeda.vo.store.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.galaxy.andromeda.util.AndromedaUtil.pickValue;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    StoreItemRepository storeItemRepository;

    @Autowired
    ProductMapper productMapper;


    @Override
    public ProductVO getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotAvailableException("Product entity for " + id +  " is not available."));

        return productMapper.mapProductToProductVO(product);
    }

    @Override
    public List<ProductVO> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return productMapper.mapProductsToProductsVO(products);
    }

    @Override
    public ProductVO saveProduct(ProductVO productVO) {

        if (productVO == null) {
            throw new InputValidationException("Product information is null to create a product");
        }

        if (StringUtils.isEmpty(productVO.getName())) {
            throw new InputValidationException("Product name is mandatory");
        }

        if (productVO.getType() == null) {
            throw new InputValidationException("Product type is mandatory");
        }

        productVO.setExternalId(AndromedaUtil.randomUUID());

        Product product = productMapper.mapProductVOToProduct(productVO);
        product.setCreatedBy("ADMIN");
        product.setCreatedOn(AndromedaUtil.getCurrentDateTime());

        return productMapper.mapProductToProductVO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotAvailableException("Product entity for " + id +  " is not available."));

        List<StoreItem> storeItems = storeItemRepository.findStoreItemsByProductId(id);

        if (storeItems != null && !storeItems.isEmpty() ) {
            throw new IntegrityViolationException(HttpStatus.BAD_REQUEST, "Product cannot be deleted as it assigned with items");
        }

        productRepository.delete(product);
    }

    @Override
    public ProductVO modifyProduct(Long id, ProductVO productVO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotAvailableException("Product entity for " + id +  " is not available."));

        product.setCode(pickValue(productVO.getCode(), product.getCode()));
        product.setName(pickValue(productVO.getName(), product.getName()));
        product.setType(pickValue(productVO.getType(), product.getType()));

        return productMapper.mapProductToProductVO(productRepository.save(product));
    }

}
