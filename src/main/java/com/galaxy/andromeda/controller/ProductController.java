package com.galaxy.andromeda.controller;

import com.galaxy.andromeda.service.ProductService;
import com.galaxy.andromeda.vo.store.ProductVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/{id}", produces = "application/json")
    public ProductVO getProduct(@PathVariable @NonNull Long id) {

        log.info("ProductController::getProduct::Start");

        ProductVO productVO = productService.getProduct(id);

        log.info("ProductController::getProduct::End");

        return productVO;
    }

    @GetMapping(produces = "application/json")
    public List<ProductVO> getAllProducts() {

        log.info("ProductController::getAllProducts::Start");

        List<ProductVO> productVOList = productService.getAllProducts();

        log.info("ProductController::getAllProducts::End");

        return productVOList;
    }

    @PostMapping(produces = "application/json")
    public ProductVO saveProduct(@Valid @RequestBody ProductVO productVO) {

        log.info("ProductController::getAllProducts::Start");

        ProductVO result = productService.saveProduct(productVO);

        log.info("ProductController::getAllProducts::End");

        return result;
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void deleteProduct(@PathVariable @NotNull Long id) {

        log.info("ProductController::getAllProducts::Start");

        productService.deleteProduct(id);

        log.info("ProductController::getAllProducts::End");
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ProductVO editProduct(@PathVariable @NotNull Long id,
                                 @RequestBody @NotNull ProductVO productVO) {

        log.info("ProductController::editProduct::Start");

        ProductVO result = productService.modifyProduct(id, productVO);

        log.info("ProductController::editProduct::End");

        return result;
    }
}
