package com.galaxy.andromeda.service.remote;

import com.galaxy.andromeda.vo.external.GithubRepositoryInfoVO;
import com.galaxy.andromeda.vo.GithubSearchInfoVO;

public interface GithubRepositoryService {

    GithubRepositoryInfoVO getGithubRepositories(GithubSearchInfoVO githubSearchInfoVO);

}
