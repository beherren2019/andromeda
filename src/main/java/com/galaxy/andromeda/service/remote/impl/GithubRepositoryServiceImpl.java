package com.galaxy.andromeda.service.remote.impl;

import com.galaxy.andromeda.exception.ExternalServiceException;
import com.galaxy.andromeda.vo.external.GithubRepositoryInfoVO;
import com.galaxy.andromeda.vo.GithubSearchInfoVO;
import com.galaxy.andromeda.service.remote.GithubRepositoryService;
import com.galaxy.andromeda.util.GitHubSearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service(value = "githubRepositoryServiceImpl")
public class GithubRepositoryServiceImpl implements GithubRepositoryService {

    @Value("${andromeda.external.github.base.url}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GithubRepositoryInfoVO getGithubRepositories(GithubSearchInfoVO githubSearchInfoVO) {

        log.info("GithubRepositoryServiceImpl::getGithubRepositories::Start");

        GithubRepositoryInfoVO externalResult;

        try {
            String url = GitHubSearchUtil.buildGithubUrl(baseUrl, githubSearchInfoVO);

            externalResult = restTemplate.getForObject(url, GithubRepositoryInfoVO.class);

        } catch (HttpClientErrorException e) {
            throw new ExternalServiceException("Exception while calling external GitHub service!", e);
        } catch (HttpServerErrorException e) {
            throw new ExternalServiceException("Exception while calling external GitHub service!", e);
        } catch (Exception e) {
            throw new ExternalServiceException("Exception while calling external GitHub service!");
        }

        log.info("GithubRepositoryServiceImpl::getGithubRepositories::End");

        return externalResult;
    }


}
