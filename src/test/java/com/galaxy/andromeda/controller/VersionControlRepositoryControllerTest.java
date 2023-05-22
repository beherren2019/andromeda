package com.galaxy.andromeda.controller;

import com.galaxy.andromeda.vo.GithubResponseVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VersionControlRepositoryControllerTest {

    @Value("${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void whenRequestWithValidInputsReturnSuccess() {

        GithubResponseVO githubResponseVO = this.restTemplate
                .getForObject("http://localhost:" + port + "/repositories/github?date=2020-10-10&pageNo=1&pageSize=10", GithubResponseVO.class);

        assertEquals(10, githubResponseVO.getItemsPerRequest());
        assertEquals(10, githubResponseVO.getItems().size());
    }
}
