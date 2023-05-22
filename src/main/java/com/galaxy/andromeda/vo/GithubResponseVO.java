package com.galaxy.andromeda.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GithubResponseVO {

    private Long total;

    private boolean isIncompleteResult;

    private int itemsPerRequest;

    private List<GithubDataVO> items;
}
