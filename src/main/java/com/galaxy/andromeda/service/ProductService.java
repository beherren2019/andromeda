package com.galaxy.andromeda.service;

import com.galaxy.andromeda.vo.store.ProductVO;

import java.util.List;

public interface ProductService {

    ProductVO getProduct(Long id);

    List<ProductVO> getAllProducts();

    ProductVO saveProduct(ProductVO productVO);

    void deleteProduct(Long id);

    ProductVO modifyProduct(Long id, ProductVO productVO);
}
