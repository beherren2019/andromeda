package com.galaxy.andromeda.service.impl;

import com.galaxy.andromeda.mapper.GithubResponseMapper;
import com.galaxy.andromeda.vo.GithubResponseVO;
import com.galaxy.andromeda.vo.external.GithubRepositoryInfoVO;
import com.galaxy.andromeda.vo.GithubSearchInfoVO;
import com.galaxy.andromeda.service.VersionControlRepositoryService;
import com.galaxy.andromeda.service.remote.GithubRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service("versionControlRepositoryServiceImpl")
public class VersionControlRepositoryServiceImpl implements VersionControlRepositoryService {

    @Autowired
    @Qualifier(value = "githubRepositoryServiceImpl")
    private GithubRepositoryService githubRepositoryService;

    @Autowired
    private GithubResponseMapper githubResponseMapper;

    @Override
    public GithubResponseVO getGithubRepositories(int pageNo, int pageSize, String sortBy, String sortDirection, String language, LocalDate date) {

        log.info("VersionControlRepositoryServiceImpl::getGithubRepositories::Start");

        GithubSearchInfoVO githubSearchInfoVO = GithubSearchInfoVO.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .date(date)
                .language(language)
                .build();

        GithubRepositoryInfoVO githubRepositoryInfoVO = githubRepositoryService.getGithubRepositories(githubSearchInfoVO);

        GithubResponseVO githubResponseVO = githubResponseMapper.map(githubRepositoryInfoVO);

        log.info("VersionControlRepositoryServiceImpl::getGithubRepositories::End");

        return githubResponseVO;
    }
}
