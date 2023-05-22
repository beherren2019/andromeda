package com.galaxy.andromeda.controller;

import com.galaxy.andromeda.vo.GithubResponseVO;
import com.galaxy.andromeda.service.VersionControlRepositoryService;
import com.galaxy.andromeda.util.AndromedaConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/repositories")
@Validated
public class VersionControlRepositoryController {

    @Autowired
    VersionControlRepositoryService versionControlRepositoryService;

    @RequestMapping(value = "/github", method = RequestMethod.GET, produces = "application/json")
    public GithubResponseVO getSearchedRepositories(
            @RequestParam(value = "pageNo", defaultValue = AndromedaConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AndromedaConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AndromedaConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = AndromedaConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection,
            @RequestParam(value = "programmingLanguage", required = false) String language,
            @Valid @NonNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @RequestParam(value = "date", required = true) LocalDate date
            ) {

        log.info("VersionControlRepositoryController::getSearchedRepositories::Start");

        GithubResponseVO githubResponseVO = versionControlRepositoryService.getGithubRepositories(pageNo, pageSize, sortBy, sortDirection, language, date);

        log.info("VersionControlRepositoryController::getSearchedRepositories::End");

        return githubResponseVO;
    }
}
