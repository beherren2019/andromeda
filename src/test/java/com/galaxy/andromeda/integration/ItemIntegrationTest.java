package com.galaxy.andromeda.integration;

import com.galaxy.andromeda.vo.store.StoreItemSearchListVO;
import com.galaxy.andromeda.vo.store.StoreItemVO;
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
public class ItemIntegrationTest {

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
    public void whenGetAllItemReturnSuccess() {

        ResponseEntity<StoreItemSearchListVO> responseEntity = this.restTemplate.exchange(baseUrl() + "/items", HttpMethod.GET,
                null, new ParameterizedTypeReference<StoreItemSearchListVO>() {});

        List<StoreItemVO> storeItems = responseEntity.getBody().getStoreItems();

        StoreItemVO storeItemVO = storeItems.get(0);

        assertEquals(9, storeItems.size());

        assertEquals(1, storeItemVO.getId());
        assertEquals("2a088ca8-32eb-4b18-aa18-e617121837a1",storeItemVO.getExternalId());
        assertEquals("item 010",storeItemVO.getName());
        assertEquals(1,storeItemVO.getStoreId());
        assertEquals(3, storeItemVO.getProductId());
    }

    @Test
    @Order(2)
    public void whenPostItemReturnSuccess() {

        StoreItemVO storeItemVO = exampleVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StoreItemVO> request = new HttpEntity<>(storeItemVO, headers);

        StoreItemVO storeItemVO1 = this.restTemplate.postForObject(baseUrl()
                + "/stores/2/products/2/items", request, StoreItemVO.class);

        assertEquals(2, storeItemVO1.getProductId());
        assertEquals(2, storeItemVO1.getStoreId());
        assertEquals("prod 2 Item", storeItemVO1.getName());
        assertEquals(5, storeItemVO1.getItemQuantity());
    }

    @Test
    @Order(3)
    public void whenEditItemReturnSuccess() {

        StoreItemVO storeItemVO = exampleVO();
        storeItemVO.setName("12312");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StoreItemVO> request = new HttpEntity<>(storeItemVO, headers);

        ResponseEntity<StoreItemVO> resp = this.restTemplate.exchange(baseUrl()
                + "/stores/1/products/3/items/1", HttpMethod.PATCH, request, StoreItemVO.class);

        StoreItemVO storeItemVO1 = resp.getBody();

        assertEquals(3, storeItemVO1.getProductId());
        assertEquals(1, storeItemVO1.getStoreId());
        assertEquals("12312", storeItemVO1.getName());
        assertEquals(5, storeItemVO1.getItemQuantity());
    }

    @Test
    @Order(4)
    public void whenDeleteItemReturnSuccess() {

        this.restTemplate.delete(baseUrl() + "/stores/1/products/3/items/1}");
    }

    private StoreItemVO exampleVO() {

        return StoreItemVO.builder()
                .storeId(2l)
                .productId(2l)
                .name("prod 2 Item")
                .itemQuantity(5)
        .build();
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }
}
