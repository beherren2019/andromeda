package com.galaxy.andromeda.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubDataVO {

    private Long id;

    private String nodeId;

    private String name;

    private String fullName;

    private boolean isPrivate;

    private ResponseOwnerVO owner;

    private String htmlUrl;

    private String description;

    private boolean fork;

    private LinksVO urls;

    private String homepage;

    private Long size;

    private Long stargazersCount;

    private Long watchersCount;

    private String language;

    private boolean hasIssues;

    private boolean hasProjects;

    private boolean hasDownloads;

    private boolean hasWiki;

    private boolean hasPages;

    private boolean hasDiscussions;

    private Long forksCount;

    private boolean archived;

    private boolean disabled;

    private Long openIssuesCount;

    private ResponseLicenseVO license;

    private boolean isAllowForking;

    private boolean isTemplate;

    private boolean isWebCommitSignOffRequired;

    private String[] topics;

    private String visibility;

    private String defaultBranch;

    private String score;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    private LocalDateTime pushedOn;
}
