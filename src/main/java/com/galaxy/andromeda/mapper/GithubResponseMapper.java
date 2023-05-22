package com.galaxy.andromeda.mapper;

import com.galaxy.andromeda.vo.GithubDataVO;
import com.galaxy.andromeda.vo.GithubResponseVO;
import com.galaxy.andromeda.vo.LinksVO;
import com.galaxy.andromeda.vo.OwnerLinksVO;
import com.galaxy.andromeda.vo.ResponseLicenseVO;
import com.galaxy.andromeda.vo.ResponseOwnerVO;
import com.galaxy.andromeda.vo.external.GithubRepositoryInfoVO;
import com.galaxy.andromeda.vo.external.GithubRepositoryItemVO;
import com.galaxy.andromeda.vo.external.LicenseVO;
import com.galaxy.andromeda.vo.external.RepositoryOwnerVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GithubResponseMapper {

    public GithubResponseVO map(GithubRepositoryInfoVO githubRepositoryInfoVO) {

        GithubResponseVO githubResponseVO = GithubResponseVO.builder()
                .total(githubRepositoryInfoVO.getTotal())
                .isIncompleteResult(githubRepositoryInfoVO.isIncompleteResult())
                .itemsPerRequest(githubRepositoryInfoVO.getGithubRepositoryItemVOList() != null
                        ? githubRepositoryInfoVO.getGithubRepositoryItemVOList().size() : 0)
                .items(mapGithubDataList(githubRepositoryInfoVO.getGithubRepositoryItemVOList()))
                .build();

        return githubResponseVO;
    }

    private List<GithubDataVO> mapGithubDataList(List<GithubRepositoryItemVO> githubRepositoryListDtoList) {

        if (githubRepositoryListDtoList == null) {
            return null;
        }

        List<GithubDataVO> githubDataListVOList =  githubRepositoryListDtoList.stream()
                .map(data -> mapGithubData(data))
                .collect(Collectors.toList());

        return githubDataListVOList;
    }

    private GithubDataVO mapGithubData(GithubRepositoryItemVO githubRepositoryItemVO) {

        GithubDataVO githubDataListVO = GithubDataVO.builder()
                .id(githubRepositoryItemVO.getId())
                .nodeId(githubRepositoryItemVO.getNodeId())
                .name(githubRepositoryItemVO.getName())
                .fullName(githubRepositoryItemVO.getFullName())
                .isPrivate(githubRepositoryItemVO.isPrivate())
                .owner(mapOwner(githubRepositoryItemVO.getOwner()))
                .htmlUrl(githubRepositoryItemVO.getHtmlUrl())
                .description(githubRepositoryItemVO.getDescription())
                .fork(githubRepositoryItemVO.isFork())
                .urls(mapLinks(githubRepositoryItemVO))
                .homepage(githubRepositoryItemVO.getHomepage())
                .size(githubRepositoryItemVO.getSize())
                .stargazersCount(githubRepositoryItemVO.getStargazersCount())
                .watchersCount(githubRepositoryItemVO.getWatchersCount())
                .language(githubRepositoryItemVO.getLanguage())
                .hasIssues(githubRepositoryItemVO.isHasIssues())
                .hasProjects(githubRepositoryItemVO.isHasProjects())
                .hasDownloads(githubRepositoryItemVO.isHasDownloads())
                .hasWiki(githubRepositoryItemVO.isHasWiki())
                .hasPages(githubRepositoryItemVO.isHasPages())
                .hasDiscussions(githubRepositoryItemVO.isHasDiscussions())
                .forksCount(githubRepositoryItemVO.getForksCount())
                .archived(githubRepositoryItemVO.isArchived())
                .disabled(githubRepositoryItemVO.isDisabled())
                .openIssuesCount(githubRepositoryItemVO.getOpenIssuesCount())
                .license(mapLicense(githubRepositoryItemVO.getLicense()))
                .isAllowForking(githubRepositoryItemVO.isAllowForking())
                .isTemplate(githubRepositoryItemVO.isTemplate())
                .isWebCommitSignOffRequired(githubRepositoryItemVO.isWebCommitSignOffRequired())
                .topics(githubRepositoryItemVO.getTopics())
                .visibility(githubRepositoryItemVO.getVisibility())
                .defaultBranch(githubRepositoryItemVO.getDefaultBranch())
                .score(githubRepositoryItemVO.getScore())
                .createdOn(githubRepositoryItemVO.getCreatedOn())
                .updatedOn(githubRepositoryItemVO.getUpdatedOn())
                .pushedOn(githubRepositoryItemVO.getPushedOn())
                .build();

        return githubDataListVO;
    }

    private ResponseLicenseVO mapLicense(LicenseVO license) {

        if (license == null) {
            return null;
        }

        ResponseLicenseVO responseLicenseVO = ResponseLicenseVO.builder()
                .key(license.getKey())
                .name(license.getName())
                .spdxId(license.getSpdxId())
                .url(license.getUrl())
                .nodeId(license.getNodeId())
                .build();

        return responseLicenseVO;
    }

    private ResponseOwnerVO mapOwner(RepositoryOwnerVO repositoryOwnerVO) {

        if (repositoryOwnerVO == null) {
            return null;
        }

        ResponseOwnerVO responseOwnerVO = ResponseOwnerVO.builder()
                .login(repositoryOwnerVO.getLogin())
                .id(repositoryOwnerVO.getId())
                .nodeId(repositoryOwnerVO.getNodeId())
                .gravatarId(repositoryOwnerVO.getGravatarId())
                .urls(mapOwnerLinks(repositoryOwnerVO))
                .type(repositoryOwnerVO.getType())
                .siteAdmin(repositoryOwnerVO.isSiteAdmin())
                .build();

        return responseOwnerVO;
    }

    private OwnerLinksVO mapOwnerLinks(RepositoryOwnerVO repositoryOwnerVO) {

        OwnerLinksVO ownerLinksVO = OwnerLinksVO.builder()
                .url(repositoryOwnerVO.getUrl())
                .avatarUrl(repositoryOwnerVO.getAvatarUrl())
                .htmlUrl(repositoryOwnerVO.getHtmlUrl())
                .followersUrl(repositoryOwnerVO.getFollowersUrl())
                .followingUrl(repositoryOwnerVO.getFollowingUrl())
                .gistsUrl(repositoryOwnerVO.getGistsUrl())
                .starredUrl(repositoryOwnerVO.getStarredUrl())
                .subscriptionsUrl(repositoryOwnerVO.getSubscriptionsUrl())
                .organizationsUrl(repositoryOwnerVO.getOrganizationsUrl())
                .eventUrl(repositoryOwnerVO.getEventUrl())
                .reposUrl(repositoryOwnerVO.getReposUrl())
                .receivedEventsUrl(repositoryOwnerVO.getReceivedEventsUrl())
                .build();

        return ownerLinksVO;
    }

    private LinksVO mapLinks(GithubRepositoryItemVO githubRepositoryItemVO) {

        LinksVO linksVO = LinksVO.builder()
                .url(githubRepositoryItemVO.getUrl())
                .forksUrl(githubRepositoryItemVO.getForksUrl())
                .keysUrl(githubRepositoryItemVO.getKeysUrl())
                .collaboratorsUrl(githubRepositoryItemVO.getCollaboratorsUrl())
                .teamsUrl(githubRepositoryItemVO.getTeamsUrl())
                .hooksUrl(githubRepositoryItemVO.getHooksUrl())
                .issueEventsUrl(githubRepositoryItemVO.getIssueEventsUrl())
                .eventsUrl(githubRepositoryItemVO.getEventsUrl())
                .assigneesUrl(githubRepositoryItemVO.getAssigneesUrl())
                .branchesUrl(githubRepositoryItemVO.getBranchesUrl())
                .tagsUrl(githubRepositoryItemVO.getTagsUrl())
                .blobsUrl(githubRepositoryItemVO.getBlobsUrl())
                .gitTagsUrl(githubRepositoryItemVO.getTagsUrl())
                .gitRefsUrl(githubRepositoryItemVO.getGitRefsUrl())
                .treesUrl(githubRepositoryItemVO.getTreesUrl())
                .statusesUrl(githubRepositoryItemVO.getStatusesUrl())
                .languagesUrl(githubRepositoryItemVO.getLanguagesUrl())
                .stargazersUrl(githubRepositoryItemVO.getStargazersUrl())
                .contributorsUrl(githubRepositoryItemVO.getContributorsUrl())
                .subscribersUrl(githubRepositoryItemVO.getSubscribersUrl())
                .subscriptionUrl(githubRepositoryItemVO.getSubscriptionUrl())
                .commitsUrl(githubRepositoryItemVO.getCommitsUrl())
                .gitCommitsUrl(githubRepositoryItemVO.getGitCommitsUrl())
                .commentsUrl(githubRepositoryItemVO.getCommentsUrl())
                .issueCommentUrl(githubRepositoryItemVO.getIssueCommentUrl())
                .contentsUrl(githubRepositoryItemVO.getContentsUrl())
                .compareUrl(githubRepositoryItemVO.getCompareUrl())
                .mergesUrl(githubRepositoryItemVO.getMergesUrl())
                .archiveUrl(githubRepositoryItemVO.getArchiveUrl())
                .downloadsUrl(githubRepositoryItemVO.getDownloadsUrl())
                .issuesUrl(githubRepositoryItemVO.getIssuesUrl())
                .pullsUrl(githubRepositoryItemVO.getPullsUrl())
                .milestonesUrl(githubRepositoryItemVO.getMilestonesUrl())
                .notificationsUrl(githubRepositoryItemVO.getNotificationsUrl())
                .labelsUrl(githubRepositoryItemVO.getLabelsUrl())
                .releasesUrl(githubRepositoryItemVO.getReleasesUrl())
                .deploymentsUrl(githubRepositoryItemVO.getDeploymentsUrl())
                .gitUrl(githubRepositoryItemVO.getGitUrl())
                .sshUrl(githubRepositoryItemVO.getSshUrl())
                .cloneUrl(githubRepositoryItemVO.getCloneUrl())
                .svnUrl(githubRepositoryItemVO.getSvnUrl())
                .mirrorUrl(githubRepositoryItemVO.getMirrorUrl())
                .build();

        return linksVO;
    }

}
