package com.galaxy.andromeda.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GithubSearchInfoVO {

    private int pageNo;

    private int pageSize;

    private String sortBy;

    private String sortDirection;

    private String language;

    private LocalDate date;
}
