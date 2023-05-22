package com.galaxy.andromeda.integration;

import com.galaxy.andromeda.enums.ProductType;
import com.galaxy.andromeda.vo.store.ProductVO;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeAll
    public void setUp() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        restTemplate.getRestTemplate().setRequestFactory(requestFactory);
    }

    @Test
    @Order(1)
    public void whenGetProductByIdReturnSuccess() {

        ProductVO productVO = this.restTemplate
                .getForObject("http://localhost:" + port + "/products/1", ProductVO.class);

        assertEquals(1, productVO.getId());
        assertEquals("914c9981-06dd-4735-bc93-4f910fb82830",productVO.getExternalId());
        assertEquals("Product 011",productVO.getName());
        assertEquals("00PR001INTL",productVO.getCode());
        assertEquals(ProductType.VEGAN, productVO.getType());
    }

    @Test
    @Order(2)
    public void whenGetAllProductsReturnSuccess() {

        ResponseEntity<List<ProductVO>> resp = this.restTemplate
                .exchange("http://localhost:" + port + "/products", HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductVO>>() {});

        List<ProductVO> products = resp.getBody();

        ProductVO productVO = products.get(0);

        assertEquals(3, products.size());
        assertEquals(1, productVO.getId());
        assertEquals("914c9981-06dd-4735-bc93-4f910fb82830",productVO.getExternalId());
        assertEquals("Product 011",productVO.getName());
        assertEquals("00PR001INTL",productVO.getCode());
        assertEquals(ProductType.VEGAN, productVO.getType());

        productVO = products.get(1);
        assertEquals(2, productVO.getId());
        assertEquals("930b9ded-a5ea-4a93-993e-04240be43e5b",productVO.getExternalId());
        assertEquals("Product 233",productVO.getName());
        assertEquals("00PR231INTL",productVO.getCode());
        assertEquals(ProductType.VEG, productVO.getType());

        productVO = products.get(2 );
        assertEquals(3, productVO.getId());
        assertEquals("7b1ed512-bf62-4a78-b75c-68ae4e031472",productVO.getExternalId());
        assertEquals("Product 678",productVO.getName());
        assertEquals("00PR908INTL",productVO.getCode());
        assertEquals(ProductType.NON_VEG, productVO.getType());
    }

    @Test
    @Order(3)
    public void whenPostNewProductReturnCreatedProduct() {

        ProductVO productVO = exampleVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ProductVO> request = new HttpEntity<>(productVO, headers);

        ProductVO product = this.restTemplate.postForObject("http://localhost:" + port + "/products", request, ProductVO.class);

        assertEquals("example name",product.getName());
        assertEquals("example",product.getCode());
        assertEquals(ProductType.VEG, product.getType());
    }


    @Test
    @Order(4)
    public void whenEditExistingStoreReturnEditedStore() {

        ProductVO productVO = exampleVO();
        productVO.setCode("fasfsdf");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ProductVO> request = new HttpEntity<>(productVO, headers);

        ResponseEntity<ProductVO> resp = this.restTemplate
                .exchange("http://localhost:" + port + "/products/" + 1, HttpMethod.PATCH,
                        request, ProductVO.class);

        ProductVO product = resp.getBody();

        assertEquals(1, product.getId());
        assertEquals("914c9981-06dd-4735-bc93-4f910fb82830",product.getExternalId());
        assertEquals("example name",product.getName());
        assertEquals("fasfsdf",product.getCode());
        assertEquals(ProductType.VEG, product.getType());
    }

    @Test
    @Order(5)
    public void whenDeleteExistingStoreDeleteStore() {

        ProductVO productVO = exampleVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ProductVO> request = new HttpEntity<>(productVO, headers);

        ProductVO productVO1 = this.restTemplate.postForObject("http://localhost:" + port + "/products", request, ProductVO.class);

        this.restTemplate.delete("http://localhost:" + port + "/products/" + productVO1.getId());

    }

    private ProductVO exampleVO() {
        return ProductVO.builder()
                .code("example")
                .name("example name")
                .type(ProductType.VEG)
                .build();
    }
}
