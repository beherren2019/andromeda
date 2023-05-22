package com.galaxy.andromeda.vo.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GithubRepositoryInfoVO {

    @JsonProperty("total_count")
    private Long total;

    @JsonProperty("incomplete_results")
    private boolean isIncompleteResult;

    @JsonProperty("items")
    private List<GithubRepositoryItemVO> githubRepositoryItemVOList;
}
