package com.galaxy.andromeda.service;

import com.galaxy.andromeda.vo.GithubResponseVO;

import java.time.LocalDate;

public interface VersionControlRepositoryService {

    GithubResponseVO getGithubRepositories(int pageNo, int pageSize, String sortBy, String sortDirection, String language, LocalDate date);

}