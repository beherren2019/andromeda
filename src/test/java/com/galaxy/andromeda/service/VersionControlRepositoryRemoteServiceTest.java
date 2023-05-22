package com.galaxy.andromeda.service;

import com.galaxy.andromeda.mapper.GithubResponseMapper;
import com.galaxy.andromeda.vo.GithubDataVO;
import com.galaxy.andromeda.vo.GithubResponseVO;
import com.galaxy.andromeda.vo.GithubSearchInfoVO;
import com.galaxy.andromeda.vo.external.GithubRepositoryInfoVO;
import com.galaxy.andromeda.vo.external.GithubRepositoryItemVO;
import com.galaxy.andromeda.service.remote.GithubRepositoryService;
import com.galaxy.andromeda.service.impl.VersionControlRepositoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class VersionControlRepositoryRemoteServiceTest {

    @InjectMocks
    VersionControlRepositoryServiceImpl versionControlRepositoryService;

    @Mock
    GithubRepositoryService githubRepositoryService;

    @Mock
    GithubResponseMapper githubResponseMapper;

    @Test
    public void whenRemoteServiceProvidesResponse_returnSuccess()
    {
        LocalDate date = LocalDate.now();

        GithubRepositoryInfoVO githubRepositoryInfoVO = githubRepositoryInfoVo();

        GithubResponseVO githubResponseVO = githubResponseVO();

        given(githubRepositoryService.getGithubRepositories(any(GithubSearchInfoVO.class))).willReturn(githubRepositoryInfoVO);

        given(githubResponseMapper.map(any(GithubRepositoryInfoVO.class))).willReturn(githubResponseVO);

        GithubResponseVO githubResponseVOResult = versionControlRepositoryService.getGithubRepositories(10, 1, "stars",
                "desc", "java", date);

        assertEquals(1, githubResponseVOResult.getItemsPerRequest());
        assertEquals(1000l, githubResponseVOResult.getTotal());
        assertTrue(githubResponseVOResult.isIncompleteResult());
    }

    @Test
    public void whenRemoteServiceThrowsHttpClientProblem_throwException()
    {
        LocalDate date = LocalDate.now();

        given(githubRepositoryService.getGithubRepositories(any(GithubSearchInfoVO.class))).willThrow(HttpClientErrorException.class);

        //test
        assertThrows(HttpClientErrorException.class, () -> {
            versionControlRepositoryService.getGithubRepositories(10, 1, "stars",
                    "desc", "java", date);
        } );
    }

    @Test
    public void whenRemoteServiceThrowsHttpServerProblem_throwException()
    {
        LocalDate date = LocalDate.now();

        given(githubRepositoryService.getGithubRepositories(any(GithubSearchInfoVO.class))).willThrow(HttpServerErrorException.class);

        //test
        assertThrows(HttpServerErrorException.class, () -> {
            versionControlRepositoryService.getGithubRepositories(10, 1, "stars",
                    "desc", "java", date);
        } );
    }

    private GithubRepositoryInfoVO githubRepositoryInfoVo() {

        return GithubRepositoryInfoVO.builder()
                .total(1000l)
                .isIncompleteResult(true)
                .githubRepositoryItemVOList(Arrays.asList(
                        GithubRepositoryItemVO.builder()
                                .nodeId("node_id_abc")
                                .build()
                ))
                .build();
    }

    private GithubResponseVO githubResponseVO() {
        return GithubResponseVO.builder()
                .total(1000l)
                .isIncompleteResult(true)
                .itemsPerRequest(1)
                .items(Arrays.asList(
                        GithubDataVO.builder()
                                .nodeId("node_id_abc")
                                .build()
                ))
                .build();
    }
}
