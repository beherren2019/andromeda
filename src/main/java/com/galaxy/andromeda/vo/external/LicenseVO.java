package com.galaxy.andromeda.vo.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LicenseVO {

    private String key;

    private String name;

    @JsonProperty("spdx_id")
    private String spdxId;

    private String url;

    @JsonProperty("node_id")
    private String nodeId;
}
