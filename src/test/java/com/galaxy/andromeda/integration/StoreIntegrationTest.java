package com.galaxy.andromeda.integration;

import com.galaxy.andromeda.vo.store.StoreVO;
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
public class StoreIntegrationTest {

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
    public void whenGetStoreByIdReturnSuccess() {

        StoreVO storeVO = this.restTemplate
                .getForObject("http://localhost:" + port + "/stores/1", StoreVO.class);

        assertEquals(1, storeVO.getId());
        assertEquals("472f35d1-a1cd-4919-80d0-ffc0d344a074",storeVO.getExternalId());
        assertEquals("MyStores GmbH",storeVO.getName());
        assertEquals("00S001BERDE",storeVO.getCode());
        assertEquals("10", storeVO.getStreetNumber());
        assertEquals("Abcdstr",storeVO.getStreetName());
        assertEquals("10121",storeVO.getZipcode());
        assertEquals("Berlin",storeVO.getCity());
        assertEquals("DE",storeVO.getCountry());
    }

    @Test
    @Order(2)
    public void whenGetStoreAllStoresReturnSuccess() {

        ResponseEntity<List<StoreVO>> resp = this.restTemplate
                .exchange("http://localhost:" + port + "/stores", HttpMethod.GET, null, new ParameterizedTypeReference<List<StoreVO>>() {});

        List<StoreVO> stores = resp.getBody();

        StoreVO storeVO = stores.get(0);

        assertEquals(3, stores.size());
        assertEquals(1, storeVO.getId());
        assertEquals("472f35d1-a1cd-4919-80d0-ffc0d344a074",storeVO.getExternalId());
        assertEquals("MyStores GmbH",storeVO.getName());
        assertEquals("00S001BERDE",storeVO.getCode());
        assertEquals("10", storeVO.getStreetNumber());
        assertEquals("Abcdstr",storeVO.getStreetName());
        assertEquals("10121",storeVO.getZipcode());
        assertEquals("Berlin",storeVO.getCity());
        assertEquals("DE",storeVO.getCountry());

        storeVO = stores.get(1);
        assertEquals(2, storeVO.getId());
        assertEquals("5250b14c-c43e-4ab6-b5fb-4bac38790089",storeVO.getExternalId());
        assertEquals("Raffael GmbH",storeVO.getName());
        assertEquals("00S011MUNDE",storeVO.getCode());
        assertEquals("89", storeVO.getStreetNumber());
        assertEquals("Efghstr",storeVO.getStreetName());
        assertEquals("80109",storeVO.getZipcode());
        assertEquals("Munich",storeVO.getCity());
        assertEquals("DE",storeVO.getCountry());

        storeVO = stores.get(2);
        assertEquals(3, storeVO.getId());
        assertEquals("da49b7dd-50fc-4bb4-a859-9dd30e6aae9a",storeVO.getExternalId());
        assertEquals("Marcus GmbH",storeVO.getName());
        assertEquals("00S061DUSDE",storeVO.getCode());
        assertEquals("55", storeVO.getStreetNumber());
        assertEquals("Hijklstr",storeVO.getStreetName());
        assertEquals("40070",storeVO.getZipcode());
        assertEquals("Dusseldorf",storeVO.getCity());
        assertEquals("DE",storeVO.getCountry());
    }

    @Test
    @Order(3)
    public void whenPostNewStoreReturnCreatedStore() {

        StoreVO storeVO = exampleStoreVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StoreVO> request = new HttpEntity<>(storeVO, headers);

        StoreVO store = this.restTemplate
                .postForObject("http://localhost:" + port + "/stores", request, StoreVO.class);

        assertEquals("example name",store.getName());
        assertEquals("example",store.getCode());
        assertEquals("20", store.getStreetNumber());
        assertEquals("street",store.getStreetName());
        assertEquals("10000",store.getZipcode());
        assertEquals("exampleCity",store.getCity());
        assertEquals("DE",store.getCountry());
    }


    @Test
    @Order(4)
    public void whenEditExistingStoreReturnEditedStore() {

        StoreVO storeVO = exampleStoreVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StoreVO> request = new HttpEntity<>(storeVO, headers);

        ResponseEntity<StoreVO> resp = this.restTemplate
                .exchange("http://localhost:" + port + "/stores/" + 1, HttpMethod.PATCH,
                        request, StoreVO.class);

        StoreVO store = resp.getBody();

        assertEquals(1,store.getId());
        assertEquals("472f35d1-a1cd-4919-80d0-ffc0d344a074",store.getExternalId());
        assertEquals("example name",store.getName());
        assertEquals("00S001BERDE",store.getCode());
        assertEquals("20", store.getStreetNumber());
        assertEquals("street",store.getStreetName());
        assertEquals("10000",store.getZipcode());
        assertEquals("exampleCity",store.getCity());
        assertEquals("DE",store.getCountry());
    }

    @Test
    @Order(5)
    public void whenDeleteExistingStoreDeleteStore() {

        StoreVO storeVO = exampleStoreVO();

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<StoreVO> request = new HttpEntity<>(storeVO, headers);

        StoreVO store = this.restTemplate
                .postForObject("http://localhost:" + port + "/stores", request, StoreVO.class);

        this.restTemplate.delete("http://localhost:" + port + "/stores/" + store.getId());

    }

    private StoreVO exampleStoreVO() {
        return StoreVO.builder()
                .code("example")
                .city("exampleCity")
                .name("example name")
                .streetName("street")
                .streetNumber("20")
                .zipcode("10000")
                .country("DE")
                .build();
    }
}
