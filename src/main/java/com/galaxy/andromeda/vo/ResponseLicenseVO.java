package com.galaxy.andromeda.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLicenseVO {

    private String key;

    private String name;

    private String spdxId;

    private String url;

    private String nodeId;
}
